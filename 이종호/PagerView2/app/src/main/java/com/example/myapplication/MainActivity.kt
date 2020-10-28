package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val bgColors = mutableListOf(
            Memo("제목1","작성자1","내용1","2020-10-28",android.R.color.white),
            Memo("제목2","작성자2","내용2","2020-10-29",android.R.color.holo_blue_light),
            Memo("제목3","작성자3","내용3","2020-10-30",android.R.color.holo_orange_light),
            Memo("제목4","작성자4","내용4","2020-10-31",android.R.color.holo_red_light),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = PagerRecyclerAdapter(bgColors)
        pager.orientation = ViewPager2.ORIENTATION_VERTICAL
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Toast.makeText(baseContext, "onPageSelected", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val MENU_ID_RECYCLER_ADAPTER = 100
        private const val MENU_ID_FRAGMENT_ADAPTER = 101
        private const val MENU_ID_ADD_ITEM = 103
    }
}