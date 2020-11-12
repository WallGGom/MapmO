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

    //swipe
    lateinit var gestureDetector: GestureDetector
    var x2:Float = 0.0f
    var x1:Float = 0.0f
    var y2:Float = 0.0f
    var y1:Float = 0.0f

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


//        setFragment1()
//        setFragment2()

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

//    fun setFragment1(){
//        val fragmentMonth : FragmentMonthDate = FragmentMonthDate()
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.monthdate, fragmentMonth)
//        transaction.commit()
//    }

//    fun setFragment2(){
//        val fragmentMemo : FragmentMonthMemo = FragmentMonthMemo()
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.monthmemo, fragmentMemo)
//        transaction.commit()
//    }


    //calendar
//        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
// 달력 날짜가 선택되면
//            diaryTextView.visibility = View.VISIBLE // 해당 날짜가 뜨는 textView가 Visible
//            save_Btn.visibility = View.VISIBLE // 저장 버튼이 Visible
//            contextEditText.visibility = View.VISIBLE // EditText가 Visible
//            textView2.visibility = View.INVISIBLE // 저장된 일기 textView가 Invisible
//            cha_Btn.visibility = View.INVISIBLE // 수정 Button이 Invisible
//            del_Btn.visibility = View.INVISIBLE // 삭제 Button이 Invisible

//            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
// 날짜를 보여주는 텍스트에 해당 날짜를 넣는다.
//            contextEditText.setText("") // EditText에 공백값 넣기
//
//            checkedDay(year, month, dayOfMonth) // checkedDay 메소드 호출
//
//
//        }

//        save_Btn.setOnClickListener { // 저장 Button이 클릭되면
//            saveDiary(fname) // saveDiary 메소드 호출
//
//            Toast.makeText(this, fname+"데이터를 저장했습니다.", Toast.LENGTH_SHORT).show()
//            str = contextEditText.getText().toString() // str 변수에 edittext내용을 toString
////형으로 저장
//            textView2.text = "${str}" // textView에 str 출력
//            save_Btn.visibility = View.INVISIBLE
//            cha_Btn.visibility = View.VISIBLE
//            del_Btn.visibility = View.VISIBLE
//            contextEditText.visibility = View.INVISIBLE
//            textView2.visibility = View.VISIBLE
//        }
//    }
//
//    fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {
//        fname = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"
// 저장할 파일 이름 설정. Ex) 2019-01-20.txt
//        var fis: FileInputStream? = null // FileStream fis 변수 설정
//
//        try {
//            fis = openFileInput(fname) // fname 파일 오픈!!
//
//            val fileData = ByteArray(fis.available()) // fileData에 파이트 형식
////으로 저장
//            fis.read(fileData) // fileData를 읽음
//            fis.close()
//
//            str = String(fileData) // str 변수에 fileData를 저장
//
//            contextEditText.visibility = View.INVISIBLE
//            textView2.visibility = View.VISIBLE
//            textView2.text = "${str}" // textView에 str 출력
//
//            save_Btn.visibility = View.INVISIBLE
//            cha_Btn.visibility = View.VISIBLE
//            del_Btn.visibility = View.VISIBLE
//
//            cha_Btn.setOnClickListener { // 수정 버튼을 누를 시
//                contextEditText.visibility = View.VISIBLE
//                textView2.visibility = View.INVISIBLE
//                contextEditText.setText(str) // editText에 textView에 저장된
// 내용을 출력
//                save_Btn.visibility = View.VISIBLE
//                cha_Btn.visibility = View.INVISIBLE
//                del_Btn.visibility = View.INVISIBLE
//                textView2.text = "${contextEditText.getText()}"
//            }
//
//            del_Btn.setOnClickListener {
//                textView2.visibility = View.INVISIBLE
//                contextEditText.setText("")
//                contextEditText.visibility = View.VISIBLE
//                save_Btn.visibility = View.VISIBLE
//                cha_Btn.visibility = View.INVISIBLE
//                del_Btn.visibility = View.INVISIBLE
//                removeDiary(fname)
//
//                Toast.makeText(this, fname+"데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show()
//            }
//
//            if(textView2.getText() == ""){
//                textView2.visibility = View.INVISIBLE
//                diaryTextView.visibility = View.VISIBLE
//                save_Btn.visibility = View.VISIBLE
//                cha_Btn.visibility = View.INVISIBLE
//                del_Btn.visibility = View.INVISIBLE
//                contextEditText.visibility = View.VISIBLE
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }



//    @SuppressLint("WrongConstant")
//    fun saveDiary(readyDay: String) {
//        var fos: FileOutputStream? = null
//
//        try {
//            fos = openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
//            var content: String = contextEditText.getText().toString()
//            fos.write(content.toByteArray())
//            fos.close()
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    @SuppressLint("WrongConstant")
//    fun removeDiary(readyDay: String) {
//        var fos: FileOutputStream? = null
//
//        try {
//            fos = openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
//            var content: String = ""
//            fos.write(content.toByteArray())
//            fos.close()
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//
}