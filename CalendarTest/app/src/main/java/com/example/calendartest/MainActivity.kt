package com.example.calendartest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var today = listOf<Int>()
    var helper:WeekRoomHelper? = null
    private var dayList = mutableListOf<WeekRoom>()
    var presentYear = 0
    var presentMonth = 0
    var presentWeek = mutableListOf<Int>(0,0,0,0,0,0,0)
    var presentWeek2 = mutableListOf(mutableListOf(0,0,0,0,0,0,0))
    var prevResult = mutableMapOf<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        helper = Room.databaseBuilder(this, WeekRoomHelper::class.java, "room_week").allowMainThreadQueries().build()
//        var temp = helper?.weekRoomDao()?.getAll()
//        Log.e("temp", temp.toString())
//        dayList.addAll(helper?.weekRoomDao()?.getAll() ?: mutableListOf())
        today = timeGenerator()
        presentYear = today[0]
        presentMonth = today[1]
        presentWeek = Week.WeekCal(today[0],today[1],today[2],today[3])
        present_year.text = presentYear.toString()
        present_month.text = presentMonth.toString()
        present_week.text = presentWeek.toString()
        Log.e("weekNo", today[4].toString())

        presentWeek2 = Month.MonthCal(today[0],today[1],today[2],today[3], today[4])
        present_week2.text = presentWeek2.toString()
        // 메모 저장 버튼 클릭이벤트
        previous_week.setOnClickListener {
            // Log.d("What the type", "${dateTv.text}")

            prevResult = Week.PreWeek(presentYear,presentMonth,presentWeek)
            Log.e("week", prevResult.toString())
            presentYear = prevResult.get("year") as Int
            presentMonth = prevResult.get("month") as Int
            presentWeek = prevResult.get("week") as MutableList<Int>
            present_year.text = presentYear.toString()
            present_month.text = presentMonth.toString()
            present_week.text = presentWeek.toString()

        }

        next_week.setOnClickListener {
            // Log.d("What the type", "${dateTv.text}")
            prevResult = Week.NxtWeek(presentYear,presentMonth,presentWeek)
            Log.e("week", prevResult.toString())
            presentYear = prevResult.get("year") as Int
            presentMonth = prevResult.get("month") as Int
            presentWeek = prevResult.get("week") as MutableList<Int>
            present_year.text = presentYear.toString()
            present_month.text = presentMonth.toString()
            present_week.text = presentWeek.toString()

        }

        previous_month.setOnClickListener {

        }

        next_month.setOnClickListener {

        }
    }
}

fun timeGenerator() : List<Int> {
    val instance = Calendar.getInstance()
    val year = instance.get(Calendar.YEAR)
    var month = (instance.get(Calendar.MONTH) + 1)
    var date = instance.get(Calendar.DATE)
    val day = instance.get(Calendar.DAY_OF_WEEK)
    val weekNo = instance.get(Calendar.WEEK_OF_MONTH)

    return listOf(year, month, date, day, weekNo)
}