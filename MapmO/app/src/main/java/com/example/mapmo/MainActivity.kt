package com.example.mapmo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
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
import androidx.core.app.ActivityCompat
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_memo_form.*

class MainActivity : AppCompatActivity() {
    var helper:MemoRoomHelper? = null
    lateinit var mr : MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 음성메모
        var path = getExternalFilesDir(null).toString()+"myrec.3gp"
//        var path = Environment.getExternalStorageDirectory().toString()+"myrec.3gp"
        mr = MediaRecorder()

        btnRecord?.isEnabled = false
        btnStop?.isEnabled = false
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)
        btnRecord?.isEnabled = true

        btnRecord?.setOnClickListener{
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
            mr.setOutputFile(path)
            mr.prepare()
            mr.start()
            btnStop.isEnabled = true
            btnRecord.isEnabled = false
        }

        btnStop?.setOnClickListener{
            mr.stop()
            btnRecord.isEnabled = true
            btnRecord.isEnabled = false

        }

        btnPlay?.setOnClickListener{
            var mp = MediaPlayer()
            mp.setDataSource(path)
            mp.prepare()
            mp.start()
        }

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

        // MemoFrom 액티비티를 intent로 설정
        val formintent = Intent(this, MemoFormActivity::class.java)
        // 메모생성 버튼을 누르면 startActivity동작 - intent
        btnCreate.setOnClickListener{ startActivity(formintent) }

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