package com.andriodproject.safe.Common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

class convert_timestamp {
    @RequiresApi(Build.VERSION_CODES.O)
    fun localDateTimeToDate(localDateTime: LocalDateTime): Date {
        // Convert LocalDateTime to ZonedDateTime using the system default timezone
        val zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault())
        // Convert ZonedDateTime to Date
        return Date.from(zonedDateTime.toInstant())
    }
}