package com.alex.habitsapp.feature.home.data.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.toStartOfDateTimestamp():Long{
    return truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toZonedDateTime(): ZonedDateTime{
    return ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.toTimeStamp(): Long{
    return this.toInstant().toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toZonedDateTime(): ZonedDateTime{
    return this.atStartOfDay(ZoneId.systemDefault())
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalTime.toZonedDateTime(): ZonedDateTime{
    return this.atDate(LocalDate.now()).atZone(ZoneId.systemDefault())
}