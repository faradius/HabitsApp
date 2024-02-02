package com.alex.habitsapp.feature.home.data.sync

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alex.habitsapp.feature.home.data.local.HomeDao
import com.alex.habitsapp.feature.home.data.local.entity.HabitSyncEntity
import com.alex.habitsapp.feature.home.data.mapper.toDomain
import com.alex.habitsapp.feature.home.data.mapper.toDto
import com.alex.habitsapp.feature.home.data.remote.HomeApi
import com.alex.habitsapp.feature.home.data.remote.util.resultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

//El work manager si permite el uso de inyección de dependencias pero se debe poner el @Assisted en el constructor
@HiltWorker
class HabitSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: HomeApi,
    private val dao: HomeDao
) : CoroutineWorker(context, workerParameters) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        //Obetenemos todos los items que queremos sincronizar
        //no es necesario correrlo en una corrutina ya que el worker ya corre en una corrutina
        val items = dao.getAllHabitsSync()

        //Son tres intentos de sincronización y si falla ya no lo vuelve a intentar y se marca como fallido
        //se vuelve a intentar si se habre nuevamente la aplicación
        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        //Ejecuta la sincronización de los items
        return try {
            supervisorScope {
                //Ejecuta por cada elemento una corrutina, si son 5 elemento son 5 corrutinas
                //esto hace que se sincronice de manera paralela 100 * 3 segundos = 3 segundos
                val jobs = items.map { items -> launch { sync(items) } }
                jobs.forEach { it.join() }

            }
            Result.success()
        } catch (e: Exception) {
            //Si falla la sincronización se intenta de nuevo
            Result.retry()
        }

        //esto hace que se sincronice de manera secuencial 100 * 3 segundos = 300 segundos
        /*items.forEach{
            sync(it.id)
        }
        return Result.success()*/
    }

    //por cada elemento que queremos sincronizar verificamos la api y si esta actualizado borramos los datos en la base de datos
    //de la nueva tabla
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun sync(entity: HabitSyncEntity) {
        //Nos traemos el habito completo
        val habit = dao.getHabitById(entity.id).toDomain().toDto()
        resultOf {
            api.insertHabit(habit)
        }.onSuccess {
            dao.deleteHabitSync(entity)
        }.onFailure {
            throw it
        }
    }
}
