package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 일 단위로 메모 보여주기로 이동
        btn_to_memoList.setOnClickListener{
            val memoListIntent = Intent(this, MemoListActivity::class.java)
            startActivity(memoListIntent)
            finish()
        }


    }


}