package com.example.frontend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_month.*


class MemoMonthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)

        //주 단위로 보여주기로 이동
        btn_month_to_week.setOnClickListener{
            val memoMtW = Intent(this, MemoWeekActivity::class.java)
            startActivity(memoMtW)
        }

        btn_month_to_day.setOnClickListener{
            val memoMtD = Intent(this, MemoListActivity::class.java)
            startActivity(memoMtD)
        }

    }
}