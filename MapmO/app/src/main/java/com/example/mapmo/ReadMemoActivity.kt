package com.example.mapmo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_read_memo.*

class ReadMemoActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detailmenu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_memo)

        var title = intent.getStringExtra("title").toString()
        var content = intent.getStringExtra("content").toString()
        var alarmdate = intent.getStringExtra("alarmdate").toString()
        var alarmtime = intent.getStringExtra("alarmtime").toString()
        var alarmcheck = intent.getBooleanExtra("alarmcheck", false)
        var alarmsettime = intent.getStringExtra("alarmsettime").toString()

        readTitle.text = title
        readContent.text = content
        readDateTv.text = alarmdate
        readTimeTv.text = alarmtime
        readSwitchAlarm.isClickable = false
        readSwitchAlarm.isChecked = alarmcheck
        readAlarmSettime.text = alarmsettime

        goToUpdate()
    }

    fun goToUpdate() {

    }
}