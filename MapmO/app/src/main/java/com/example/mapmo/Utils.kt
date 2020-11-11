package com.example.mapmo

import java.util.*
object Utils {
    fun timeGenerator() : List<Int> {
        val instance = Calendar.getInstance()
        val year = instance.get(Calendar.YEAR)
        var month = (instance.get(Calendar.MONTH) + 1)
        var date = instance.get(Calendar.DATE)
        val day = instance.get(Calendar.DAY_OF_WEEK)
        val weekNo = instance.get(Calendar.WEEK_OF_MONTH)

        return listOf(year, month, date, day, weekNo)
    }
}