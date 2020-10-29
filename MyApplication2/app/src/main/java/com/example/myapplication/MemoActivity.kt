package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_memo.*
import java.util.*

class MemoActivity : AppCompatActivity() {
    var helper:MemoRoomHelper? = null
    val random = Random()
    private fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        helper = Room.databaseBuilder(this, MemoRoomHelper::class.java, "room_memo").allowMainThreadQueries().build()

        buttonSave.setOnClickListener {
            if (editTilte.text.toString().isNotEmpty() && editContent.text.toString().isNotEmpty()) {
                val memo = MemoRoom(editTilte.text.toString(), editContent.text.toString(), editPlace.text.toString(), System.currentTimeMillis())
                helper?.memoRoomDao()?.insert(memo)
                Log.d("memo", memo.title)
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }
        }
    }
}