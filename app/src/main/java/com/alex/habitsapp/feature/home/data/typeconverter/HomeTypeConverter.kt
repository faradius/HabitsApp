package com.alex.habitsapp.feature.home.data.typeconverter

import android.util.Log
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.util.joinIntoString
import androidx.room.util.splitToIntList
import com.squareup.moshi.Moshi
import java.lang.NumberFormatException

@ProvidedTypeConverter
class HomeTypeConverter{
    //Se hace esta conversión de datos por que room no acepta listas de objetos, por lo que nececitamos
    //convertirlos a string para poder guardarlos en la base de datos, en este caso en JSON con Moshi
    @TypeConverter
    fun fromFrequency(days: List<Int>): String {
        return joinIntoString(days) ?: ""
    }

    @TypeConverter
    fun toFrequency(value: String): List<Int> {
        return splitToIntList(value) ?: emptyList()
    }

    @TypeConverter
    fun fromCompletedDates(days: List<Long>): String {
        return joinIntoString(days) ?: ""
    }

    @TypeConverter
    fun toCompletedDates(value: String): List<Long> {
        return splitToLongList(value) ?: emptyList()
    }

    private fun splitToLongList(input: String?): List<Long>? {
        return input?.split(',')?.mapNotNull { item ->
            try {
                item.toLong()
            } catch (ex: NumberFormatException) {
                null
            }
        }
    }

    private fun joinIntoString(input: List<Long>?): String? {
        return input?.joinToString(",")
    }
}