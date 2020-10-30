package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_month.*
import kotlinx.android.synthetic.main.activity_week.*


class MemoWeekActivity : AppCompatActivity(), GestureDetector.OnGestureListener  {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week)
        gestureDetector = GestureDetector(this, this)


        // 메모 숨기기
        week_image1.visibility = View.GONE
        week_memoTitle1.visibility = View.GONE
        week_memoContent1.visibility = View.GONE
        week_alarm.visibility = View.GONE
        week_editTextDate.visibility = View.GONE
        week_imageView2.visibility = View.GONE

        week_image3.visibility = View.GONE
        week_memoTitle2.visibility = View.GONE
        week_memoContent2.visibility = View.GONE
        week_alarm2.visibility = View.GONE
        week_editTextDate2.visibility = View.GONE
        week_imageView4.visibility = View.GONE




//        btn_week_to_day.setOnClickListener{
//            val memoWtD = Intent(this, MemoListActivity::class.java)
//            startActivity(memoWtD)
//        }
//
//        btn_week_to_month.setOnClickListener{
//            val memoWtM = Intent(this, MemoMonthActivity::class.java)
//            startActivity(memoWtM)
//        }

        // 버튼 누르면 해당 날짜에 해당하는 메모들 보여주기
        tuedayTxtView.setOnClickListener{

            Toast.makeText(this, "3일의 메모를 보여줍니다.", Toast.LENGTH_SHORT).show()

            week_image1.visibility = View.VISIBLE
            week_memoTitle1.visibility = View.VISIBLE
            week_memoContent1.visibility = View.VISIBLE
            week_alarm.visibility = View.VISIBLE
            week_editTextDate.visibility = View.VISIBLE
            week_imageView2.visibility = View.VISIBLE

            week_image3.visibility = View.GONE
            week_memoTitle2.visibility = View.GONE
            week_memoContent2.visibility = View.GONE
            week_alarm2.visibility = View.GONE
            week_editTextDate2.visibility = View.GONE
            week_imageView4.visibility = View.GONE
        }

        weddayTxtView.setOnClickListener{

            Toast.makeText(this, "4일의 메모를 보여줍니다.", Toast.LENGTH_SHORT).show()

            week_image3.visibility = View.VISIBLE
            week_memoTitle2.visibility = View.VISIBLE
            week_memoContent2.visibility = View.VISIBLE
            week_alarm2.visibility = View.VISIBLE
            week_editTextDate2.visibility = View.VISIBLE
            week_imageView4.visibility = View.VISIBLE

            week_image1.visibility = View.GONE
            week_memoTitle1.visibility = View.GONE
            week_memoContent1.visibility = View.GONE
            week_alarm.visibility = View.GONE
            week_editTextDate.visibility = View.GONE
            week_imageView2.visibility = View.GONE
        }

        // progressbar
//        title = "주 단위로 메모 보여주기"
//        progressBar = findViewById(R.id.progressBar)
//        progressBar.max = 100
//        progressBar.progress = 50


    }
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