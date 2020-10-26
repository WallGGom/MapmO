package com.example.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentList = listOf(Day(), Week(), Month())

        val adapter = FragmentAdapter(supportFragmentManager,1)
        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter
    }
}