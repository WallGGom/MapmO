package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var helper:MemoRoomHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = Room.databaseBuilder(this, MemoRoomHelper::class.java, "room_memo").allowMainThreadQueries().build()
        val adapter = PagerRecyclerAdapter()
        adapter.helper = helper
        adapter.listData.addAll(helper?.memoRoomDao()?.getAll() ?: mutableListOf())

        pager.adapter = adapter
        pager.orientation = ViewPager2.ORIENTATION_VERTICAL
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Toast.makeText(baseContext, "onPageSelected", Toast.LENGTH_SHORT).show()
            }
        })
        fab1.setOnClickListener{
            val memoIntent = Intent(this, MemoActivity::class.java)
            startActivity(memoIntent)
        }

        fab2.setOnClickListener {
            adapter.listData.clear()
            adapter.listData.addAll(helper?.memoRoomDao()?.getAll() ?: mutableListOf())
            adapter.notifyDataSetChanged()
        }

    }

    companion object {
        private const val MENU_ID_RECYCLER_ADAPTER = 100
        private const val MENU_ID_FRAGMENT_ADAPTER = 101
        private const val MENU_ID_ADD_ITEM = 103
    }
}