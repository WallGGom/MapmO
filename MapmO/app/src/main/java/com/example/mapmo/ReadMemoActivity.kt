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
        var title = intent.getStringExtra("title").toString()
        var content = intent.getStringExtra("content").toString()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_memo)
        readTitle.text = title
        readContent.text = content
    }

    fun goToUpdate() {

    }
}