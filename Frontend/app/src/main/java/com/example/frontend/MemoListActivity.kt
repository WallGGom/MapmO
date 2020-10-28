package com.example.frontend

import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_day.*



class MemoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        //주 단위로 보여주기로 이동
        btn_day_to_week.setOnClickListener{
            val memoDtW = Intent(this, MemoWeekActivity::class.java)
            startActivity(memoDtW)
            finish()
        }

        //월 단위로 보여주기로 이동
        btn_day_to_month.setOnClickListener{
            val memoDtM = Intent(this, MemoMonthActivity::class.java)
            startActivity(memoDtM)
            finish()
        }

        // swipe

        activityDay.setOnTouchListener(object: OnSwipeTouchListener(this@MemoListActivity) {

            override fun onSwipeLeft() {
                Toast.makeText(this@MemoListActivity,"왼쪽으로", Toast.LENGTH_SHORT).show()
            }
            override fun onSwipeRight() {
                Toast.makeText(this@MemoListActivity,"오른쪽으로", Toast.LENGTH_SHORT).show()
            }
            override fun onSwipeTop() {
                Toast.makeText(this@MemoListActivity,"위로", Toast.LENGTH_SHORT).show()
            }
            override fun onSwipeBottom() {
                Toast.makeText(this@MemoListActivity,"아래로", Toast.LENGTH_SHORT).show()
            }

        })

    }
}
