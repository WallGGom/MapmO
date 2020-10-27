package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_to_login.setOnClickListener{
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        btn_to_register.setOnClickListener{
            val signupIntent = Intent(this, SignupActivity::class.java)
            startActivity(signupIntent)
        }
    }
}
