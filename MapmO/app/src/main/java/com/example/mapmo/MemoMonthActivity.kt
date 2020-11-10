package com.example.mapmo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_month.*

class MemoMonthActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    var today = Utils.timeGenerator()
    var presentYear2 = 0
    var presentMonth2 = 0
    var presentWeek2 = mutableListOf(0,0,0,0,0,0,0)
    var monthResult = mutableMapOf<String, Any>()
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
        gestureDetector = GestureDetector(this, this)
        presentYear2 = today[0]
        presentMonth2 = today[1]
        presentWeek2 = Month.MonthCal(today[0],today[1],today[2],today[3], today[4])
        Log.e("weeks", presentWeek2.toString())
        var data:MutableList<ListMonthData> = setMonthData(presentWeek2)
        var adapter = MonthDateAdapter()
        month2.text = presentYear2.toString() + "년" + presentMonth2.toString() + "월"

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
            month2.text = presentYear2.toString() + "년" + presentMonth2.toString() + "월"
            //Log.e("하이", presentMonth2.toString())
            //Log.e("바이", presentYear2.toString())
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
            month2.text = presentYear2.toString() + "년" + presentMonth2.toString() + "월"
        }


//        setFragment1()
        setFragment2()

    }
    fun setMonthData(list: MutableList<Int>): MutableList<ListMonthData>{
        var data:MutableList<ListMonthData> = mutableListOf()
        for (num in list) {
            var listData = ListMonthData(num)
            data.add(listData)
        }
        return data
    }

//    fun setFragment1(){
//        val fragmentMonth : FragmentMonthDate = FragmentMonthDate()
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.monthdate, fragmentMonth)
//        transaction.commit()
//    }

    fun setFragment2(){
        val fragmentMemo : FragmentMonthMemo = FragmentMonthMemo()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.monthmemo, fragmentMemo)
        transaction.commit()
    }


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


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)

        when (event?.action){

            //when we start to swipe
            0->
            {
                x1 = event.x
                y1 = event.y
            }


            //when we end to swipe
            1->
            {
                x2 = event.x
                y2 = event.y

                val valueX:Float = x2-x1
                val valueY:Float = y2-y1


                if (Math.abs(valueX) > MemoListActivity.MIN_DISTANCE) {

                    //detect right side swipe
                    if (x2 > x1) {
                        Toast.makeText(this, "Right swipe", Toast.LENGTH_SHORT).show()
                        val memoMtW = Intent(this, MemoWeekActivity::class.java)
                        startActivity(memoMtW)
                        finish()
                    }
                    //detect left side swipe
                    else {
                        Toast.makeText(this, "Left swipe", Toast.LENGTH_SHORT).show()
                        val memoMtD = Intent(this, MemoListActivity::class.java)
                        startActivity(memoMtD)
                        finish()


                    }

                }
                else if (Math.abs(valueY) > MemoListActivity.MIN_DISTANCE){
                    //detect top to bottom swipe
                    if(y2>y1)
                    {
                        Toast.makeText(this, "Bottom swipe", Toast.LENGTH_SHORT).show()
                    }
                    //detect bottom to top swipe
                    else
                    {
                        Toast.makeText(this, "Top swipe", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        return super.onTouchEvent(event)
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