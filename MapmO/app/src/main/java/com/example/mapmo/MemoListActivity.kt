package com.example.mapmo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_day.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MemoListActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    lateinit var gestureDetector: GestureDetector
    var x2:Float = 0.0f
    var x1:Float = 0.0f
    var y2:Float = 0.0f
    var y1:Float = 0.0f

    var helper:MemoRoomHelper? = null
    private var dayList = mutableListOf<MemoRoom>()
    lateinit var dayAdapter: MemoRecyclerAdapter

    companion object{
        const val MIN_DISTANCE = 150

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        gestureDetector = GestureDetector(this, this)

        //현재 날짜 가져오기
        var now = LocalDate.now()
        var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//        editTextDate.text = Strnow
//        helper = Room.databaseBuilder(this, MemoRoomHelper::class.java, "room_memo").allowMainThreadQueries().build()
//        var temp = helper?.memoRoomDao()?.getAll()
//        Log.e("temp", temp.toString())
//        dayList.addAll(helper?.memoRoomDao()?.getAll() ?: mutableListOf())
//        Log.e("list", dayList.toString())
//
//
//        val adapter = MemoRecyclerAdapter(dayList, 1)
//        adapter.helper = helper
//        adapter.notifyDataSetChanged()
//
//        day_rec.adapter = adapter
//        day_rec.layoutManager = LinearLayoutManager(this)
//        day_rec.setHasFixedSize(true)


        //주 단위로 보여주기로 이동
//        btn_day_to_week.setOnClickListener{
//            val memoDtW = Intent(this, MemoWeekActivity::class.java)
//            startActivity(memoDtW)
//
//        }

        //월 단위로 보여주기로 이동
//        btn_day_to_month.setOnClickListener{
//            val memoDtM = Intent(this, MemoMonthActivity::class.java)
//            startActivity(memoDtM)
//
//        }



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


                if (Math.abs(valueX) > MIN_DISTANCE) {

                    //detect right side swipe
                    if (x2 > x1) {
                        Toast.makeText(this, "Right swipe", Toast.LENGTH_SHORT).show()
                        val memoDtM = Intent(this, MemoMonthActivity::class.java)
                        startActivity(memoDtM)
                        finish()
                    }
                    //detect left side swipe
                    else {
                        Toast.makeText(this, "Left swipe", Toast.LENGTH_SHORT).show()
                        val memoDtW = Intent(this, MemoWeekActivity::class.java)
                        startActivity(memoDtW)
                        finish()

                    }

                }
                else if (Math.abs(valueY) > MIN_DISTANCE){
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