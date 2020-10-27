package com.example.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    var signup:Signup? = null
    var check:Check? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        var tempUrl = getString(R.string.baseUrl)
        Log.d("temp", tempUrl)
        var emuUrl = getString(R.string.baseUrlDev)
        Log.d("emu", emuUrl)
        var retrofit = Retrofit.Builder()
            .baseUrl(emuUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var signupService: SignupService = retrofit.create(SignupService::class.java)

        btn_name_val.setOnClickListener{
            var checkname = signupName.text.toString()

            Log.d("ID", checkname)
            signupService.requestCheck(checkname).enqueue(object: Callback<Check> {
                override fun onFailure(call: Call<Check>, t: Throwable) {
                    Log.d("CometChatAPI::", "Failed API call with call: " + call + " + exception: " + t)
                    var dialog = AlertDialog.Builder(this@SignupActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출 실패")
                    dialog.show()
                }

                override fun onResponse(call: Call<Check>, response: Response<Check>) {
                    check = response.body()
                    Log.d("CHECK", "msg : "+check?.result)
                    var dialog = AlertDialog.Builder(this@SignupActivity)
                    dialog.setTitle("Check Result")
                    dialog.setMessage(check?.result)
                    dialog.show()
                }
            })

        }

        btn_signup.setOnClickListener{
            var username = signupName.text.toString()
            var password1 = signupPw1.text.toString()
            var password2 = signupPw2.text.toString()
            Log.d("ID", username)
            Log.d("PW1", password1)
            Log.d("PW1", password2)
            signupService.requestSignup(username, password1, password2).enqueue(object: Callback<Signup> {
                override fun onFailure(call: Call<Signup>, t: Throwable) {
                    Log.d("CometChatAPI::", "Failed API call with call: " + call + " + exception: " + t)
                    var dialog = AlertDialog.Builder(this@SignupActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출 실패")
                    dialog.show()
                }

                override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                    signup = response.body()
                    Log.d("SIGNUP", "msg : "+signup?.result)
                    var dialog = AlertDialog.Builder(this@SignupActivity)
                    dialog.setTitle("Signup Result")
                    dialog.setMessage("받았엉")
                    dialog.show()
                }
            })
        }
    }
}