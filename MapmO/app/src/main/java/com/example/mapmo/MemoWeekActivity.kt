package com.example.mapmo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi

class MemoWeekActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    //swipe
    lateinit var gestureDetector: GestureDetector
    var x2:Float = 0.0f
    var x1:Float = 0.0f
    var y2:Float = 0.0f
    var y1:Float = 0.0f

    companion object{
        const val MIN_DISTANCE = 150
    }

    //progressbar
    lateinit var progressBar: ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week)
        gestureDetector = GestureDetector(this, this)

        setFragment1()
        setFragment2()
    }

    fun setFragment1(){
        val fragmentWeek : FragmentWeekDate = FragmentWeekDate()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.weekdate,FragmentWeekDate.newInstance())
        transaction.commit()
    }

    fun setFragment2(){
        val fragmentMemo : FragmentWeekMemo = FragmentWeekMemo()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.weekmemo,FragmentWeekMemo.newInstance())
        transaction.commit()
    }

    // 메모 숨기기
//        week_image1.visibility = View.GONE
//        week_memoTitle1.visibility = View.GONE
//        week_memoContent1.visibility = View.GONE
//        week_alarm.visibility = View.GONE
//        week_editTextDate.visibility = View.GONE
//        week_imageView2.visibility = View.GONE
//
//        week_image3.visibility = View.GONE
//        week_memoTitle2.visibility = View.GONE
//        week_memoContent2.visibility = View.GONE
//        week_alarm2.visibility = View.GONE
//        week_editTextDate2.visibility = View.GONE
//        week_imageView4.visibility = View.GONE


    //현재 날짜 가져오기
//        val instance = Calendar.getInstance()
//        val today_year = instance.get(Calendar.YEAR) //년
//        val today_month = (instance.get(Calendar.MONTH)+1) //월
//        val today_date = instance.get(Calendar.DATE) //일
//        val today_day = instance.get(Calendar.DAY_OF_WEEK) //요일
//        val last_month = today_month -1 //지난 달
//
//        var mon_date = mutableMapOf(
//                1 to 31,
//                2 to 29,
//                3 to 31,
//                4 to 30,
//                5 to 31,
//                6 to 30,
//                7 to 31,
//                8 to 31,
//                9 to 30,
//                10 to 31,
//                11 to 30,
//                12 to 31
//        )

    //요일별 id 매핑하기

//        val daymap:Map<Int, String> = mapOf(
//            1 to "sundayTxtView",
//            2 to "mondayTxtView",
//            3 to "tuedayTxtView",
//            4 to "weddayTxtView",
//            5 to "thudayTxtView",
//            6 to "fridayTxtView",
//            7 to "satdayTxtView"
//        )
//        var endDate = 0
//        var befEndDate = 0
//
//
//        // 월, 일 매핑하기
//        if (today_year %4==0) {
//            mon_date[2] = 29
//        } else{
//            mon_date[2] = 28
//        }
//
//        endDate = mon_date.get(today_month)!!
//        befEndDate = mon_date.get(last_month)!!
//
//        val Day = daymap.get(today_day) // 오늘의 요일에 해당하는 id값
//        //Log.e("test", Day)
//        val week = mutableListOf(0,0,0,0,0,0,0)
//        week[today_day-1]=today_date
//        val maxDist = 7-today_day
//        var rDay = 0
//        var lDay = 0
//
//        for (i in 1 until maxDist+1){
//            rDay = today_day +i -1
//            lDay = today_day -i -1
//            if ((0<= rDay) && (rDay<7)){
//                week[rDay]=week[rDay-1]+1
//
//            }
//
//            if ((0<= lDay) && (lDay<7)){
//                week[lDay] = week[lDay+1]-1
//                if (week[lDay]==0){
//                    week[lDay] = befEndDate
//                }
//
//            }
//        }
//
//        Log.e("test", week.toString())
//        sundayTxtView.text=week[0].toString()
//        mondayTxtView.text=week[1].toString()
//        tuedayTxtView.text=week[2].toString()
//        weddayTxtView.text=week[3].toString()
//        thudayTxtView.text=week[4].toString()
//        fridayTxtView.text=week[5].toString()
//        satdayTxtView.text=week[6].toString()
//
//




//        btn_week_to_day.setOnClickListener{
//            val meoWtD = Intent(this, MemoListActivity::class.java)
//            startActivity(memoWtD)
//        }
//
//        btn_week_to_month.setOnClickListener{
//            val memoWtM = Intent(this, MemoMonthActivity::class.java)
//            startActivity(memoWtM)
//        }



    // 버튼 누르면 해당 날짜에 해당하는 메모들 보여주기
//        tuedayTxtView.setOnClickListener{
//
//            Toast.makeText(this, "3일의 메모를 보여줍니다.", Toast.LENGTH_SHORT).show()
//
//            week_image1.visibility = View.VISIBLE
//            week_memoTitle1.visibility = View.VISIBLE
//            week_memoContent1.visibility = View.VISIBLE
//            week_alarm.visibility = View.VISIBLE
//            week_editTextDate.visibility = View.VISIBLE
//            week_imageView2.visibility = View.VISIBLE
//
//            week_image3.visibility = View.GONE
//            week_memoTitle2.visibility = View.GONE
//            week_memoContent2.visibility = View.GONE
//            week_alarm2.visibility = View.GONE
//            week_editTextDate2.visibility = View.GONE
//            week_imageView4.visibility = View.GONE
//        }
//
//        weddayTxtView.setOnClickListener{
//
//            Toast.makeText(this, "4일의 메모를 보여줍니다.", Toast.LENGTH_SHORT).show()
//
//            week_image3.visibility = View.VISIBLE
//            week_memoTitle2.visibility = View.VISIBLE
//            week_memoContent2.visibility = View.VISIBLE
//            week_alarm2.visibility = View.VISIBLE
//            week_editTextDate2.visibility = View.VISIBLE
//            week_imageView4.visibility = View.VISIBLE
//
//            week_image1.visibility = View.GONE
//            week_memoTitle1.visibility = View.GONE
//            week_memoContent1.visibility = View.GONE
//            week_alarm.visibility = View.GONE
//            week_editTextDate.visibility = View.GONE
//            week_imageView2.visibility = View.GONE
//        }

    // progressbar
//        title = "주 단위로 메모 보여주기"
//        progressBar = findViewById(R.id.progressBar)
//        progressBar.max = 100
//        progressBar.progress = 50



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
                        val memoWtD = Intent(this, MemoListActivity::class.java)
                        startActivity(memoWtD)
                    }
                    //detect left side swipe
                    else {
                        Toast.makeText(this, "Left swipe", Toast.LENGTH_SHORT).show()
                        val memoWtM = Intent(this, MemoMonthActivity::class.java)
                        startActivity(memoWtM)


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