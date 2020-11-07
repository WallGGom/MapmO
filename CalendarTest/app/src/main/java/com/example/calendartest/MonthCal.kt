package com.example.calendartest

import android.util.Log

object Month {
    var endDateMap2 = mutableMapOf(
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
    var presentYear = 0
    var presentMonth = 0
    var prevResult = mutableMapOf<String, Any>()

    fun MonthCal(year: Int, month: Int, date: Int, day: Int, weekNo: Int): MutableList<MutableList<Int>> {

        var checkTwo = 4
        var temp1 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp2 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp3 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp4 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp5 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var tempResult = mutableListOf(temp1, temp2, temp3, temp4, temp5)
        var rWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var lWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var rStep = 0
        var lStep = 0
        var presentWeek = mutableListOf<Int>(0,0,0,0,0,0,0)
        presentWeek = Week.WeekCal(year, month, date, day)

        when (weekNo) {
            1 -> {
                temp1 = presentWeek
                for (step1 in 0..3) {
                    prevResult = Week.NxtWeek(year, month, presentWeek)
                    rWeek = prevResult.get("week") as MutableList<Int>
                    when (step1) {
                        0 -> {
                            temp2 = rWeek
                        }
                        1 -> {
                            temp3 = rWeek
                        }
                        2 -> {
                            temp4 = rWeek
                        }
                        3 -> {
                            temp5 = rWeek
                        }
                    }
                    presentWeek = rWeek

                }
            }
            2 -> {
                temp2 = presentWeek
                for (step2 in 0..2) {

                }
            }
            3 -> {
                temp3 = presentWeek
                for (step3 in 0..1) {

                }
            }
            4 -> {
                temp4 = presentWeek
                for (step4 in 0..2) {

                }
            }
            5 -> {
                temp5 = presentWeek
                for (step5 in 0..3) {

                }
            }
        }
        tempResult = mutableListOf(temp1, temp2, temp3, temp4, temp5)
        return tempResult
    }

    fun PreMonth(year: Int, month: Int, week: MutableList<Int>): MutableMap<String, Any> {
        var tempDateP = 0
        var preYear = year
        var prevYear = year - 1
        var preMon = month
        var prevMon = month - 1
        var preWeek = week
        var prevWeek = mutableListOf<Int>(0,0,0,0,0,0,0)
        var cnt = 0

        if (year % 4 == 0) {
            endDateMap2[2] = 29
        } else {
            endDateMap2[2] = 28
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
                prevWeek[j] = endDateMap2.get(prevMon)!!
                Log.e("change", prevWeek[j].toString())
            } else {
                prevWeek[j] = tempDateP
            }
        }
        preWeek = prevWeek


        return mutableMapOf("year" to preYear, "month" to preMon, "week" to preWeek)
    }

    fun NxtMonth(year: Int, month: Int, week: MutableList<Int>): MutableMap<String, Any> {
        var tempDateP = 0
        var preYear = year
        var nxtYear = year + 1
        var preMon = month
        var nxtMon = month + 1
        var preWeek = week
        var nxtWeek = mutableListOf<Int>(0,0,0,0,0,0,0)
        var cnt = 0

        if (year % 4 == 0) {
            endDateMap2[2] = 29
        } else {
            endDateMap2[2] = 28
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
            if (tempDateP > endDateMap2[preMon]!!) {
                nxtWeek[j] = 1
                Log.e("change", nxtWeek[j].toString())
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