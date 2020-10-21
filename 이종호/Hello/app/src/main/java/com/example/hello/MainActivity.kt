package com.example.hello

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private var editText: EditText?=null
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text)
        sharedPreferences = getSharedPreferences("com.example.hello.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        editText = findViewById(R.id.editText)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
//            Toast.makeText(
//                this@MainActivity,
//                editText?.text.toString(),
//                Toast.LENGTH_SHORT
//            ).show()
            counter++
            val editor = sharedPreferences.edit()
            editor.putInt("COUNTER", counter)
            editor.commit()
        }
    }
    override fun onResume() {
        super.onResume()
        counter = sharedPreferences.getInt("COUNTER", counter)
        textView.text = "Counter: $counter"
    }
}

