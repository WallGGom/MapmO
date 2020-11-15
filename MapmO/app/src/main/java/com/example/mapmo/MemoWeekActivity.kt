 package com.example.mapmo

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mapmo.common.Constants
import com.example.mapmo.db.NoteDataBase
import com.example.mapmo.models.NoteModel
import com.example.mapmo.uicomponents.activities.landing.MainActivity
import com.example.mapmo.uicomponents.activities.viewnote.ViewNote
import kotlinx.android.synthetic.main.activity_week.*
import kotlinx.android.synthetic.main.activity_week.week_rec
import kotlinx.android.synthetic.main.fragment_week_memo.*
import kotlinx.android.synthetic.main.week_date_item.*
import kotlinx.android.synthetic.main.week_date_item.view.*
import java.util.*
import kotlin.concurrent.timerTask

 class MemoWeekActivity : AppCompatActivity() {
    var today = Utils.timeGenerator()
    var presentYear = 0
    var presentMonth = 0
    var previousMonth = 0
    var previousYear = 0
    var presentYearStr = ""
    var presentMonthStr = ""
    var previousMonthStr = ""
    var previousYearStr = ""
    var targetMonth = 0
    var targetYear = 0
    var targetMonthStr = ""
    var targetYearStr = ""
    var startDateStr = ""
    var endDateStr = ""
    var stdDateStr = ""
    var presentWeek = mutableListOf<Int>(0,0,0,0,0,0,0)
    var weekResult = mutableMapOf<String, Any>()
    var helper: NoteDataBase? = null
    var mNoteList: MutableList<NoteModel>? = null
    var mNoteList2: MutableList<NoteModel>? = null
    lateinit var weekAdapter: MemoRecyclerAdapter
    val linearLayoutManager by lazy { LinearLayoutManager(this) }
    var mNoteDataBase : NoteDataBase? = null





    //progressbar
    lateinit var progressBar: ProgressBar

    private lateinit var mNoteModel: NoteModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week)


        //button
        week_to_day.setOnClickListener{
            val memoWtD = Intent(this, MainActivity::class.java)
            startActivity(memoWtD)
        }

        week_to_month.setOnClickListener{
            val memoWtM = Intent(this, MemoMonthActivity::class.java)
            startActivity(memoWtM)
        }



        presentYear = today[0]
        presentMonth = today[1]

        presentYearStr = convInt(presentYear)
        presentMonthStr = convInt(presentMonth)

        presentWeek = Week.WeekCal(today[0],today[1],today[2],today[3])

        mNoteDataBase = NoteDataBase.getInstance(this)

        var data:MutableList<ListWeekData> = setWeekData(presentWeek)
        Log.e("data", data.toString())
        startDateStr = convInt(data.get(0).number)
        endDateStr = convInt(data.get(6).number)
        previousMonthStr = presentMonthStr
        previousYearStr = presentYearStr
        if (data.get(6).number < 7) {
            previousMonth = presentMonth - 1
            if (previousMonth == 0) {
                previousMonth = 12
                previousYear = presentYear - 1
                previousYearStr = convInt(previousYear)
            }
            previousMonthStr = convInt(previousMonth)
        }
        Log.e("toast", "%$previousYearStr/$previousMonthStr/$startDateStr")
        Log.e("toast", "%$presentYearStr/$presentMonthStr/$endDateStr")

        var addRunnable = Runnable {
            try {
                mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAllBetweenWeek(startDate = "$previousYearStr/$previousMonthStr/$startDateStr", endDate = "$presentYearStr/$presentMonthStr/$endDateStr")
                Log.e("temp1", mNoteList.toString())

            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }
        var addThread = Thread(addRunnable)
        addThread.start()
        var adapter = WeekDateAdapter() { listdata ->
            //Toast.makeText(this, "몇일이게? ${listdata.number}", Toast.LENGTH_SHORT).show()
            mission_date.text= listdata.number.toString() + "일"

            stdDateStr = convInt(listdata.number)
            if (listdata.flag) {
                if (listdata.flag2) {
                    targetMonth = previousMonth
                    Log.e("inside", targetMonth.toString())
                    if (targetMonth == 12) {
                        targetYear = previousYear
                        targetYearStr = convInt(targetYear)
                    }
                    presentMonthStr = convInt(presentMonth)
                } else {
                    targetMonth = presentMonth
                    targetMonthStr = convInt(targetMonth)
                    targetYearStr = convInt(presentYear)
            }
        } else {
            targetMonthStr = presentMonthStr
            targetYearStr = presentYearStr
        }

        mNoteList2 = mutableListOf()
        Log.e("toast", "%$targetYearStr/$targetMonthStr/$stdDateStr flag:${listdata.flag} flag2:${listdata.flag2}")
        for (pick in mNoteList!!) {

            if ((pick.createdAt.slice(0..9) == "$targetYearStr/$targetMonthStr/$stdDateStr") or (pick.planDate == "$targetYearStr/$targetMonthStr/$stdDateStr")) {
                    Log.e("note", pick.toString())
                    mNoteList2?.add(pick)
                }
            }
            Log.e("note", mNoteList2.toString())
            weekAdapter = MemoRecyclerAdapter(mNoteList2, 2) { memo ->
                val intent = Intent(this@MemoWeekActivity, ViewNote::class.java)
                val bundle = Bundle()
                bundle.putSerializable(Constants.SELECTED_NOTE,memo)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            weekAdapter.notifyDataSetChanged()
            week_rec.adapter = weekAdapter
            week_rec.layoutManager = linearLayoutManager
            week_rec.setHasFixedSize(true)


        }

        adapter.listData = data
        re_week_date.adapter = adapter
        re_week_date.layoutManager = GridLayoutManager(this, 7)


        // date
        what_week.text = "🗓" + presentYear.toString() + "년" + " " + presentMonth.toString() + "월"





        previous_week.setOnClickListener {
            // Log.d("What the type", "${dateTv.text}")

            weekResult = Week.PreWeek(presentYear,presentMonth,presentWeek)
//            Log.e("week", weekResult.toString())
            presentYear = weekResult.get("year") as Int
            presentMonth = weekResult.get("month") as Int
            presentWeek = weekResult.get("week") as MutableList<Int>
            data = setWeekData(presentWeek)
            adapter.listData = data
            re_week_date.adapter = adapter
            re_week_date.layoutManager = GridLayoutManager(this, 7)
            presentYearStr = convInt(presentYear)
            presentMonthStr = convInt(presentMonth)
            previousMonthStr = presentMonthStr
            previousYearStr = presentYearStr
            if (data.get(6).number < 7) {
                previousMonth = presentMonth - 1
                if (previousMonth == 0) {
                    previousMonth = 12
                    previousYear = presentYear - 1
                    previousYearStr = convInt(previousYear)
                }
                previousMonthStr = convInt(previousMonth)
            }

            startDateStr = convInt(data.get(0).number)
            endDateStr = convInt(data.get(6).number)
            Log.e("toast", "%$previousYearStr/$previousMonthStr/$startDateStr")
            Log.e("toast", "%$presentYearStr/$presentMonthStr/$endDateStr")
            addRunnable = Runnable {
                try {
//                    mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAllInDay(stdDate = "%$presentYearStr/$presentMonthStr/$stdDateStr")
                    mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAllBetweenWeek(startDate = "$previousYearStr/$previousMonthStr/$startDateStr", endDate = "$presentYearStr/$presentMonthStr/$endDateStr")
                    Log.e("temp1", mNoteList.toString())

                } catch (e: Exception) {
                    Log.d("tag", "Error - $e")
                }
            }
            addThread = Thread(addRunnable)
            addThread.start()
            weekAdapter = MemoRecyclerAdapter(mutableListOf(), 2) { memo ->
                val intent = Intent(this@MemoWeekActivity, ViewNote::class.java)
                val bundle = Bundle()
                bundle.putSerializable(Constants.SELECTED_NOTE,memo)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            weekAdapter.notifyDataSetChanged()
            week_rec.adapter = weekAdapter
            week_rec.layoutManager = linearLayoutManager
            week_rec.setHasFixedSize(true)

            // date
            what_week.text = "🗓" + presentYear.toString() + "년" + " " + presentMonth.toString() + "월"

        }

        next_week.setOnClickListener {
            // Log.d("What the type", "${dateTv.text}")
            weekResult = Week.NxtWeek(presentYear,presentMonth,presentWeek)
//            Log.e("week", weekResult.toString())
            presentYear = weekResult.get("year") as Int
            presentMonth = weekResult.get("month") as Int
            presentWeek = weekResult.get("week") as MutableList<Int>

            data = setWeekData(presentWeek)
            adapter.listData = data
            re_week_date.adapter = adapter
            re_week_date.layoutManager = GridLayoutManager(this, 7)
            presentYearStr = convInt(presentYear)
            presentMonthStr = convInt(presentMonth)
            previousMonthStr = presentMonthStr
            previousYearStr = presentYearStr
            if (data.get(6).number < 7) {
                previousMonth = presentMonth - 1
                if (previousMonth == 0) {
                    previousMonth = 12
                    previousYear = presentYear - 1
                    previousYearStr = convInt(previousYear)
                }
                previousMonthStr = convInt(previousMonth)
            }

            startDateStr = convInt(data.get(0).number)
            endDateStr = convInt(data.get(6).number)
            Log.e("toast", "%$previousYearStr/$previousMonthStr/$startDateStr")
            Log.e("toast", "%$presentYearStr/$presentMonthStr/$endDateStr")
            addRunnable = Runnable {
                try {
//                    mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAllInDay(stdDate = "%$presentYearStr/$presentMonthStr/$stdDateStr")
                    mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAllBetweenWeek(startDate = "$previousYearStr/$previousMonthStr/$startDateStr", endDate = "$presentYearStr/$presentMonthStr/$endDateStr")
                    Log.e("temp1", mNoteList.toString())

                } catch (e: Exception) {
                    Log.d("tag", "Error - $e")
                }
            }
            addThread = Thread(addRunnable)
            addThread.start()
            weekAdapter = MemoRecyclerAdapter(mutableListOf(), 2) { memo ->
                val intent = Intent(this@MemoWeekActivity, ViewNote::class.java)
                val bundle = Bundle()
                bundle.putSerializable(Constants.SELECTED_NOTE,memo)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            weekAdapter.notifyDataSetChanged()
            week_rec.adapter = weekAdapter
            week_rec.layoutManager = linearLayoutManager
            week_rec.setHasFixedSize(true)

            // date
            what_week.text = "🗓" + presentYear.toString() + "년" + " " + presentMonth.toString() + "월"

        }

    }

    fun setWeekData(list: MutableList<Int>): MutableList<ListWeekData>{
        var data:MutableList<ListWeekData> = mutableListOf()
        for (num in list) {
            var listData = ListWeekData(num, false, false)
            data.add(listData)
        }
        return data
    }

    fun convInt(num:Int): String{
        var tempNum = num.toString()
        if (tempNum.length == 1) {
            tempNum = "0$tempNum"
        }
        return tempNum
    }



    override fun onDown(e: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }
}


