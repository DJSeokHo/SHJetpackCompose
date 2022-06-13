package com.swein.framework.utility.date

import android.annotation.SuppressLint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateUtility {
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTimeString(): String {
        val calendar: Calendar = Calendar.getInstance()
        val date = calendar.time
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }

    fun getCurrentDateTimeStringWithNoSpace(connectionString: String): String {
        val calendar: Calendar = Calendar.getInstance()
        return "${calendar.get(Calendar.YEAR)}$connectionString" +
                "${(calendar.get(Calendar.MONTH) + 1)}$connectionString" +
                "${calendar.get(Calendar.DAY_OF_MONTH)}$connectionString" +
                "${calendar.get(Calendar.HOUR_OF_DAY)}$connectionString" +
                "${calendar.get(Calendar.MINUTE)}$connectionString" +
                "${calendar.get(Calendar.SECOND)}$connectionString"
    }

    fun getCurrentDateTimeMillisecondStringWithNoSpace(connectionString: String): String {
        val calendar: Calendar = Calendar.getInstance()
        return "${calendar.get(Calendar.YEAR)}$connectionString" +
                "${(calendar.get(Calendar.MONTH) + 1)}$connectionString" +
                "${calendar.get(Calendar.DAY_OF_MONTH)}$connectionString" +
                "${calendar.get(Calendar.HOUR_OF_DAY)}$connectionString" +
                "${calendar.get(Calendar.MINUTE)}$connectionString" +
                "${calendar.get(Calendar.SECOND)}$connectionString" +
                "${calendar.get(Calendar.MILLISECOND)}"
    }

    @SuppressLint("SimpleDateFormat")
    fun getDayOfWeekOfDateTime(date: String): String {
        val weekDays: List<String> = listOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")

        val cal: Calendar = Calendar.getInstance()

        try {
            cal.time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date) ?: Date()

            var w: Int = cal.get(Calendar.DAY_OF_WEEK) - 1
            if (w < 0) {
                w = 0
            }

            return weekDays[w]
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}