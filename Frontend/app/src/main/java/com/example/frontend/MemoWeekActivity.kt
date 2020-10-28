package com.example.frontend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_week.*


class MemoWeekActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week)


        btn_week_to_day.setOnClickListener{
            val memoWtD = Intent(this, MemoListActivity::class.java)
            startActivity(memoWtD)
        }

        btn_week_to_month.setOnClickListener{
            val memoWtM = Intent(this, MemoMonthActivity::class.java)
            startActivity(memoWtM)
        }

    }
}