package com.example.mapmo

import android.util.Log

object Week {
    var endDateMap = mutableMapOf(
            1 to 31,
            2 to 28,
            3 to 31,
            4 to 30,
            5 to 31,
            6 to 30,
            7 to 31,
            8 to 31,
            9 to 30,
            10 to 31,
            11 to 30,
            12 to 31
    )
    val noDay = mapOf(1 to "일", 2 to "월", 3 to "화", 4 to "수", 5 to "목", 6 to "금", 7 to "토")
    val startDay = mapOf(
            2010 to 6,
            2011 to 7,
            2012 to 1,
            2013 to 3,
            2014 to 4,
            2015 to 5,
            2016 to 6,
            2017 to 1,
            2018 to 2,
            2019 to 3,
            2020 to 4,
            2021 to 6,
            2022 to 7,
            2023 to 1,
            2024 to 2,
            2025 to 4,
            2026 to 5,
            2027 to 6,
            2028 to 7,
            2029 to 2,
            2030 to 3
    )
    val maxRange = mapOf(1 to 6, 2 to 5, 3 to 4, 4 to 3, 5 to 4, 6 to 5, 7 to 6)
    fun WeekCal(year: Int, month: Int, date: Int, day: Int): MutableList<Int> {
//        Log.e("Calendar", "$year/$month/$date/$day")
        var tempResult = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        if (year % 4 == 0) {
            endDateMap[2] = 29
        } else {
            endDateMap[2] = 28
        }
        var endDate = endDateMap[month]
        var step = maxRange[day]
        var stdDay = day - 1
        tempResult[stdDay] = date

        for (i in 1..step!!) {
            var rDay = date + i
            var lDay = date - i
            if ((stdDay + i) in 1..7) {
                tempResult[stdDay + i] = rDay
            }
            if ((stdDay - i) in 0..6) {
                tempResult[stdDay - i] = lDay
            }
        }
        return tempResult
    }

    fun PreWeek(year: Int, month: Int, week: MutableList<Int>): MutableMap<String, Any> {
        var tempDateP = 0
        var preYear = year
        var prevYear = year - 1
        var preMon = month
        var prevMon = month - 1
        var preWeek = week
        var prevWeek = mutableListOf<Int>(0,0,0,0,0,0,0)
        var cnt = 0

        if (year % 4 == 0) {
            endDateMap[2] = 29
        } else {
            endDateMap[2] = 28
        }
        if (prevMon == 0) {
            prevMon = 12
        }
        if (1 in preWeek) {
            preMon = prevMon
            if (prevMon == 12) {
                preYear = prevYear
            }
        }
        for (j in 6 downTo 0) {

            if (j == 6) {
                tempDateP = preWeek[0] - 1
            } else {
                tempDateP = prevWeek[j+1] - 1
            }
            if (tempDateP < 1) {
                prevWeek[j] = endDateMap.get(prevMon)!!
//                Log.e("change", prevWeek[j].toString())
            } else {
                prevWeek[j] = tempDateP
            }
        }
        preWeek = prevWeek


        return mutableMapOf("year" to preYear, "month" to preMon, "week" to preWeek)
    }

    fun NxtWeek(year: Int, month: Int, week: MutableList<Int>): MutableMap<String, Any> {
        var tempDateP = 0
        var preYear = year
        var nxtYear = year + 1
        var preMon = month
        var nxtMon = month + 1
        var preWeek = week
        var nxtWeek = mutableListOf<Int>(0,0,0,0,0,0,0)
        var cnt = 0

        if (year % 4 == 0) {
            endDateMap[2] = 29
        } else {
            endDateMap[2] = 28
        }
        if (nxtMon == 13) {
            nxtMon = 1
        }

        for (j in 0..6) {

            if (j == 0) {
                tempDateP = preWeek[6] + 1
            } else {
                tempDateP = nxtWeek[j-1] + 1
            }
            if (tempDateP > endDateMap[preMon]!!) {
                nxtWeek[j] = 1
//                Log.e("change", nxtWeek[j].toString())
            } else {
                nxtWeek[j] = tempDateP
            }
        }
        preWeek = nxtWeek
        if (1 in preWeek) {
            preMon = nxtMon
            if (preMon == 1) {
                preYear = nxtYear
            }
        }

        return mutableMapOf("year" to preYear, "month" to preMon, "week" to preWeek)
    }
}
