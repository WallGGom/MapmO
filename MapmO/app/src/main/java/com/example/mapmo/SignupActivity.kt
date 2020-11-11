package com.example.mapmo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        val suEmail = findViewById<EditText>(R.id.su_email)
        val suPassword1 = findViewById<EditText>(R.id.su_pw1)
        val suPassword2 = findViewById<EditText>(R.id.su_pw2)
        val suLoading = findViewById<ProgressBar>(R.id.su_loading)

        btn_signup.setOnClickListener {
            createUserEmail(suEmail.text.toString(), suPassword1.text.toString(), suPassword2.text.toString())
        }

    }

    fun createUserEmail(email : String, password1: String, password2: String) {
        if (password1 == password2) {
            var password = password1
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this, { task ->
                    if (task.isSuccessful) {
                        val user = auth?.getCurrentUser()
                    } else {
                        showSettingPopup(2)
                    }
                })

        } else {
            showSettingPopup(1)
        }

    }
    fun showSettingPopup(situation : Int) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.alert_popup, null)
        val textView: TextView = view.findViewById(R.id.textView)
        var errorTitle = ""

        if (situation == 1) {
            textView.text = "두 비밀번호가 일치하지 않습니다. 확인해주세요!"
            errorTitle = "비밀번호 오류"
        } else if (situation == 2) {
            textView.text = "회원가입에 실패했어요..ㅠ"
            errorTitle = "회원가입 실패"
        } else if (situation == 3) {
            textView.text = "로그인 성공"
            errorTitle = "로그인"
        }

        val alertDialog = AlertDialog.Builder(this)
            .setTitle(errorTitle)
            .setNeutralButton("확인", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
}