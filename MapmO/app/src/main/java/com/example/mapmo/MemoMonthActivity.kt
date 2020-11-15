package com.example.mapmo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapmo.common.Constants
import com.example.mapmo.db.NoteDataBase
import com.example.mapmo.models.NoteModel
import com.example.mapmo.uicomponents.activities.landing.MainActivity
import com.example.mapmo.uicomponents.activities.viewnote.ViewNote
import kotlinx.android.synthetic.main.activity_month.*
import kotlinx.android.synthetic.main.activity_week.*

class MemoMonthActivity : AppCompatActivity() {

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
    var today = Utils.timeGenerator()
    var presentYear2 = 0
    var presentMonth2 = 0
    var previousMonth2 = 0
    var previousYear2 = 0
    var presentYearStr2 = ""
    var presentMonthStr2 = ""
    var previousMonthStr2 = ""
    var previousYearStr2 = ""
    var startDateStr2 = ""
    var endDateStr2 = ""
    var stdDateStr2 = ""
    var presentWeek2 = mutableListOf(0,0,0,0,0,0,0)
    var monthResult = mutableMapOf<String, Any>()
    var helper: NoteDataBase? = null
    var mNoteList: MutableList<NoteModel>? = null
    var mNoteList2: MutableList<NoteModel>? = null
    var weekList = mutableListOf<NoteModel>()
    lateinit var monthAdapter: MemoRecyclerAdapter
    val linearLayoutManager by lazy { LinearLayoutManager(this) }
    var mNoteDataBase : NoteDataBase? = null

    companion object{
        const val MIN_DISTANCE = 150
    }

    //calendar
    var fname: String = ""
    var str: String = ""


    // new calendar
//    override val toolbar: Toolbar?
//        get() = null
//
//    override val titleRes: Int = R.string.example_1_title
//
//    private lateinit var binding: Example1FragmentBinding
//
//    private val selectedDates = mutableSetOf<LocalDate>()
//    private val today = LocalDate.now()
//    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)


        //button
        month_to_day.setOnClickListener{
            val memoMtD = Intent(this, MainActivity::class.java)
            startActivity(memoMtD)
        }

        month_to_week.setOnClickListener{
            val memoMtW = Intent(this, MemoWeekActivity::class.java)
            startActivity(memoMtW)
        }


        presentYear2 = today[0]
        presentMonth2 = today[1]
        if (presentYear2 % 4 == 0) {
            endDateMap[2] = 29
        } else {
            endDateMap[2] = 28
        }
        presentYearStr2 = convInt(presentYear2)
        presentMonthStr2 = convInt(presentMonth2)

        presentWeek2 = Month.MonthCal(today[0],today[1],today[2],today[3], today[4])
        Log.e("weeks", presentWeek2.toString())

        mNoteDataBase = NoteDataBase.getInstance(this)

        var data:MutableList<ListMonthData> = setMonthData(presentWeek2)
        Log.e("weeks", data.toString())
        month2.text = "🗓" + presentYear2.toString() + "년" + " " + presentMonth2.toString() + "월"
        startDateStr2 = "01"
        endDateStr2 = convInt(endDateMap[presentMonth2]!!)

        Log.e("toast", "%$presentYearStr2/$presentMonthStr2/$startDateStr2")
        Log.e("toast", "%$presentYearStr2/$presentMonthStr2/$endDateStr2")

        var addRunnable = Runnable {
            try {
                mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAllBetweenWeek(startDate = "$presentYearStr2/$presentMonthStr2/$startDateStr2", endDate = "$presentYearStr2/$presentMonthStr2/$endDateStr2")
                Log.e("temp1", mNoteList.toString())

            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }
        var addThread = Thread(addRunnable)
        addThread.start()

        var adapter = MonthDateAdapter() { listdata ->
//            Toast.makeText(this, "몇일이게? ${listdata.number}", Toast.LENGTH_SHORT).show()
            mission_date2.text= listdata.number.toString() + "일"
            stdDateStr2 = convInt(listdata.number)
            Log.e("toast", "%$presentYearStr2/$presentMonthStr2/$stdDateStr2")
            mNoteList2 = mutableListOf()
            for (pick in mNoteList!!) {
                if ((pick.createdAt.slice(0..9) == "$presentYearStr2/$presentMonthStr2/$stdDateStr2") or (pick.planDate == "$presentYearStr2/$presentMonthStr2/$stdDateStr2")) {
                    Log.e("note", pick.toString())
                    mNoteList2?.add(pick)
                }
            }
            Log.e("note", mNoteList2.toString())
            monthAdapter = MemoRecyclerAdapter(mNoteList2, 3) { memo ->
                val intent = Intent(this@MemoMonthActivity, ViewNote::class.java)
                val bundle = Bundle()
                bundle.putSerializable(Constants.SELECTED_NOTE,memo)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            monthAdapter.notifyDataSetChanged()
            month_rec.adapter = monthAdapter
            month_rec.layoutManager = linearLayoutManager
            month_rec.setHasFixedSize(true)

        }

        adapter.listData = data
        adapter.year = presentYear2
        adapter.month = presentMonth2
        adapter.flag = false
        re_month_date.adapter = adapter
        re_month_date.layoutManager = GridLayoutManager(this, 7)






        previous_month.setOnClickListener {
            monthResult = Month.PreMonth(presentYear2, presentMonth2, presentWeek2)
            Log.e("weeks", monthResult.toString())
            presentYear2 = monthResult.get("year") as Int
            presentMonth2 = monthResult.get("month") as Int
            presentWeek2 = monthResult.get("weeks") as MutableList<Int>
            data = setMonthData(presentWeek2)
            adapter.listData = data
            adapter.year = presentYear2
            adapter.month = presentMonth2
            adapter.flag = false
            re_month_date.adapter = adapter
            re_month_date.layoutManager = GridLayoutManager(this, 7)
            month2.text = "🗓" + presentYear2.toString() + "년" + " " + presentMonth2.toString() + "월"
            presentYearStr2 = convInt(presentYear2)
            presentMonthStr2 = convInt(presentMonth2)
            startDateStr2 = "01"
            endDateStr2 = convInt(endDateMap[presentMonth2]!!)
            addRunnable = Runnable {
                try {
                    mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAllBetweenWeek(startDate = "$presentYearStr2/$presentMonthStr2/$startDateStr2", endDate = "$presentYearStr2/$presentMonthStr2/$endDateStr2")
                    Log.e("temp1", mNoteList.toString())

                } catch (e: Exception) {
                    Log.d("tag", "Error - $e")
                }
            }
            addThread = Thread(addRunnable)
            addThread.start()
            monthAdapter = MemoRecyclerAdapter(mutableListOf(), 3) { memo ->
                val intent = Intent(this@MemoMonthActivity, ViewNote::class.java)
                val bundle = Bundle()
                bundle.putSerializable(Constants.SELECTED_NOTE,memo)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            monthAdapter.notifyDataSetChanged()
            month_rec.adapter = monthAdapter
            month_rec.layoutManager = linearLayoutManager
            month_rec.setHasFixedSize(true)
        }

        next_month.setOnClickListener {
            monthResult = Month.NxtMonth(presentYear2, presentMonth2, presentWeek2)
            Log.e("weeks", monthResult.toString())
            presentYear2 = monthResult.get("year") as Int
            presentMonth2 = monthResult.get("month") as Int
            presentWeek2 = monthResult.get("weeks") as MutableList<Int>
            data = setMonthData(presentWeek2)
            adapter.listData = data
            adapter.year = presentYear2
            adapter.month = presentMonth2
            adapter.flag = false
            re_month_date.adapter = adapter
            re_month_date.layoutManager = GridLayoutManager(this, 7)
            month2.text = "🗓" + presentYear2.toString() + "년"+ " " + presentMonth2.toString() + "월"
            presentYearStr2 = convInt(presentYear2)
            presentMonthStr2 = convInt(presentMonth2)
            startDateStr2 = "01"
            endDateStr2 = convInt(endDateMap[presentMonth2]!!)
            addRunnable = Runnable {
                try {
                    mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAllBetweenWeek(startDate = "$presentYearStr2/$presentMonthStr2/$startDateStr2", endDate = "$presentYearStr2/$presentMonthStr2/$endDateStr2")
                    Log.e("temp1", mNoteList.toString())

                } catch (e: Exception) {
                    Log.d("tag", "Error - $e")
                }
            }
            addThread = Thread(addRunnable)
            addThread.start()
            monthAdapter = MemoRecyclerAdapter(mutableListOf(), 3) { memo ->
                val intent = Intent(this@MemoMonthActivity, ViewNote::class.java)
                val bundle = Bundle()
                bundle.putSerializable(Constants.SELECTED_NOTE,memo)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            monthAdapter.notifyDataSetChanged()
            month_rec.adapter = monthAdapter
            month_rec.layoutManager = linearLayoutManager
            month_rec.setHasFixedSize(true)
        }


    }
    fun setMonthData(list: MutableList<Int>): MutableList<ListMonthData>{
        var data:MutableList<ListMonthData> = mutableListOf()
        for (num in list) {
            var listData = ListMonthData(num)
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
}