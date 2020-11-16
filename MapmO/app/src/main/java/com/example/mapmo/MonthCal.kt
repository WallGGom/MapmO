package com.example.mapmo

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
    var nxtResult = mutableMapOf<String, Any>()

    fun MonthCal(year: Int, month: Int, date: Int, day: Int, weekNo: Int): MutableList<Int> {

        var checkTwo = 4
        var temp1 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp2 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp3 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp4 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp5 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var tempResult = mutableListOf(0,0,0,0,0)
        var rWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var lWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var rStep = 0
        var lStep = 0
        var presentWeek = mutableListOf<Int>(0,0,0,0,0,0,0)
        var presentWeek2 = mutableListOf<Int>(0,0,0,0,0,0,0)
        presentWeek = Week.WeekCal(year, month, date, day)

        when (weekNo) {
            1 -> {
                temp1 = presentWeek
                for (step1 in 0..3) {
                    nxtResult = Week.NxtWeek(year, month, presentWeek)
                    rWeek = nxtResult.get("week") as MutableList<Int>
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
                presentWeek2 = presentWeek
                for (step2 in 0..2) {
                    nxtResult = Week.NxtWeek(year, month, presentWeek)
                    prevResult = Week.PreWeek(year, month, presentWeek2)
                    rWeek = nxtResult.get("week") as MutableList<Int>
                    lWeek = prevResult.get("week") as MutableList<Int>
                    when (step2) {
                        0 -> {
                            temp1 = lWeek
                            temp3 = rWeek
                        }
                        1 -> {
                            temp4 = rWeek
                        }
                        2 -> {
                            temp5 = rWeek
                        }
                    }
                    presentWeek = rWeek

                }
            }
            3 -> {
                temp3 = presentWeek
                presentWeek2 = presentWeek
                for (step3 in 0..1) {
                    nxtResult = Week.NxtWeek(year, month, presentWeek)
                    prevResult = Week.PreWeek(year, month, presentWeek2)
                    rWeek = nxtResult.get("week") as MutableList<Int>
                    lWeek = prevResult.get("week") as MutableList<Int>
                    when (step3) {
                        0 -> {
                            temp2 = lWeek
                            temp4 = rWeek
                        }
                        1 -> {
                            temp1 = lWeek
                            temp5 = rWeek
                        }

                    }
                    presentWeek = rWeek
                    presentWeek2 = lWeek
                }
            }
            4 -> {
                temp4 = presentWeek
                presentWeek2 = presentWeek
                for (step4 in 0..2) {
                    nxtResult = Week.NxtWeek(year, month, presentWeek)
                    prevResult = Week.PreWeek(year, month, presentWeek2)
                    rWeek = nxtResult.get("week") as MutableList<Int>
                    lWeek = prevResult.get("week") as MutableList<Int>
                    when (step4) {
                        0 -> {
                            temp3 = lWeek
                            temp5 = rWeek
                        }
                        1 -> {
                            temp2 = lWeek
                        }
                        2 -> {
                            temp1 = lWeek
                        }

                    }
                    presentWeek2 = lWeek
                }
            }
            5 -> {
                temp5 = presentWeek
                for (step5 in 0..3) {
                    prevResult = Week.PreWeek(year, month, presentWeek)
                    lWeek = prevResult.get("week") as MutableList<Int>
                    when (step5) {
                        0 -> {
                            temp4 = lWeek
                        }
                        1 -> {
                            temp3 = lWeek
                        }
                        2 -> {
                            temp2 = lWeek
                        }
                        3 -> {
                            temp1 = lWeek
                        }
                    }
                    presentWeek = lWeek

                }
            }
        }
        tempResult = temp1
        tempResult.addAll(temp2)
        tempResult.addAll(temp3)
        tempResult.addAll(temp4)
        tempResult.addAll(temp5)
        return tempResult
    }

    fun PreMonth(year: Int, month: Int, weeks: MutableList<Int>): MutableMap<String, Any> {
        var preYear = year
        var prevYear = year - 1
        var preMon = month
        var prevMon = month - 1
        var presentWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var preWeeks = weeks
        var prevWeeks = mutableListOf(0,0,0,0,0,0,0)
        var temp0 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp1 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp2 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp3 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp4 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp5 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var lWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)

        var firstWeek = preWeeks.slice(0..6)
//        Log.e("firstWeek", firstWeek.toString())
        if (year % 4 == 0) {
            endDateMap2[2] = 29
        } else {
            endDateMap2[2] = 28
        }
        if (prevMon == 0) {
            prevMon = 12
            preYear = prevYear
        }
        var prevEndDate = endDateMap2.get(prevMon)

        if (prevEndDate in firstWeek) {
            temp5 = firstWeek as MutableList<Int>
            presentWeek = temp5
            for (step5 in 0..4) {
                prevResult = Week.PreWeek(preYear, prevMon, presentWeek)
                lWeek = prevResult.get("week") as MutableList<Int>
//                if (1 in lWeek) {
//                    prevResult = Week.PreWeek(year, prevMon, presentWeek)
//                    lWeek = prevResult.get("week") as MutableList<Int>
//                }
                when (step5) {
                    0 -> {
                        temp4 = lWeek
                    }
                    1 -> {
                        temp3 = lWeek
                    }
                    2 -> {
                        temp2 = lWeek
                    }
                    3 -> {
                        temp1 = lWeek
                    }
                    4 -> {
                        temp0 = lWeek
                    }
                }
                presentWeek = lWeek

            }
        } else {
            presentWeek = firstWeek as MutableList<Int>
            prevResult = Week.PreWeek(preYear, preMon, presentWeek)
            temp5 = prevResult.get("week") as MutableList<Int>
            presentWeek = temp5
            for (step5 in 0..4) {
                prevResult = Week.PreWeek(preYear, prevMon, presentWeek)
                lWeek = prevResult.get("week") as MutableList<Int>
                when (step5) {
                    0 -> {
                        temp4 = lWeek
                    }
                    1 -> {
                        temp3 = lWeek
                    }
                    2 -> {
                        temp2 = lWeek
                    }
                    3 -> {
                        temp1 = lWeek
                    }
                }
                presentWeek = lWeek

            }
        }

        preMon = prevMon
//        Log.e("firstWeek", temp1.toString())
        if (1 in temp0) {
            prevWeeks = temp0
            prevWeeks.addAll(temp1)
            prevWeeks.addAll(temp2)
        } else if (1 in temp1) {
            prevWeeks = temp1
            prevWeeks.addAll(temp2)
        } else {
            prevWeeks = temp2
        }
        prevWeeks.addAll(temp3)
        prevWeeks.addAll(temp4)
        prevWeeks.addAll(temp5)

        return mutableMapOf("year" to preYear, "month" to preMon, "weeks" to prevWeeks)
    }

    fun NxtMonth(year: Int, month: Int, weeks: MutableList<Int>): MutableMap<String, Any> {
        var preYear = year
        var nxtYear = year + 1
        var preMon = month
        var nxtMon = month + 1
        var presentWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var preWeeks = weeks
        var nxtWeeks = mutableListOf(0,0,0,0,0,0,0)
        var temp1 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp2 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp3 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp4 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp5 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var temp0 = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var rWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        if (year % 4 == 0) {
            endDateMap2[2] = 29
        } else {
            endDateMap2[2] = 28
        }
        if (nxtMon == 13) {
            nxtMon = 1
            preYear = nxtYear
        }
        var nxtEndDate = endDateMap2.get(nxtMon)

        var lastWeek = preWeeks.slice(preWeeks.size-7..preWeeks.size-1)
//        Log.e("size", lastWeek.toString())

        if (1 in lastWeek) {
            temp1 = lastWeek as MutableList<Int>
            presentWeek = temp1
            for (step5 in 0..4) {
                nxtResult = Week.NxtWeek(preYear, nxtMon, presentWeek)
                rWeek = nxtResult.get("week") as MutableList<Int>
                when (step5) {
                    0 -> {
                        temp2 = rWeek
                    }
                    1 -> {
                        temp3 = rWeek
                    }
                    2 -> {
                        temp4 = rWeek
                        preMon = nxtMon
                    }
                    3 -> {
                        temp5 = rWeek
                    }
                    4 -> {
                        temp0 = rWeek
                    }
                }
                presentWeek = rWeek

            }
        } else {
            presentWeek = lastWeek as MutableList<Int>
            nxtResult = Week.NxtWeek(preYear, preMon, presentWeek)
            temp1 = nxtResult.get("week") as MutableList<Int>
            presentWeek = temp1
            for (step5 in 0..3) {
                nxtResult = Week.NxtWeek(preYear, preMon, presentWeek)
                rWeek = nxtResult.get("week") as MutableList<Int>
                when (step5) {
                    0 -> {
                        temp2 = rWeek
                    }
                    1 -> {
                        temp3 = rWeek
                    }
                    2 -> {
                        temp4 = rWeek
                        preMon = nxtMon
                    }
                    3 -> {
                        temp5 = rWeek
                    }
                }
                presentWeek = rWeek

            }
        }

        preMon = nxtMon

        nxtWeeks = temp1
        nxtWeeks.addAll(temp2)
        nxtWeeks.addAll(temp3)
        nxtWeeks.addAll(temp4)
//        Log.e("nxtEndDate", nxtEndDate.toString())
//        Log.e("temp5", temp5.toString())
        if (nxtEndDate in temp5) {
            nxtWeeks.addAll(temp5)
        } else if (nxtEndDate in temp0) {
            nxtWeeks.addAll(temp5)
            nxtWeeks.addAll(temp0)
        }


        return mutableMapOf("year" to preYear, "month" to preMon, "weeks" to nxtWeeks)
    }
}


