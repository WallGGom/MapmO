package com.example.mapmo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mapmo.ui.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    var helper:MemoRoomHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        helper = Room.databaseBuilder(this, MemoRoomHelper::class.java, "room_memo").allowMainThreadQueries().build()
//        val adapter = MemoRecyclerAdapter(this)
//        adapter.helper = helper
//        adapter.listData.addAll(helper?.memoRoomDao()?.getAll() ?: mutableListOf())
//
//        pager.adapter = adapter
//        pager.orientation = ViewPager2.ORIENTATION_VERTICAL
//        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                Toast.makeText(baseContext, "onPageSelected", Toast.LENGTH_SHORT).show()
//            }
//        })

        // MemoFrom 액티비티를 intent로 설정
        val formintent = Intent(this, MemoFormActivity::class.java)
        // 메모생성 버튼을 누르면 startActivity동작 - intent
        btnCreate.setOnClickListener{ startActivity(formintent) }

        // 일 단위로 메모 보여주기로 이동
        btn_to_memoList.setOnClickListener{
            val memoListIntent = Intent(this, MemoListActivity::class.java)
            startActivity(memoListIntent)
        }

        btn_to_bu.setOnClickListener {
            val backIntent = Intent(this, BackupActivity::class.java)
            startActivity(backIntent)
        }

        btn_to_login.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

    }

    companion object {
        private const val MENU_ID_RECYCLER_ADAPTER = 100
        private const val MENU_ID_FRAGMENT_ADAPTER = 101
        private const val MENU_ID_ADD_ITEM = 103
    }
}