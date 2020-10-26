package com.example.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var login:Login? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var tempUrl = getString(R.string.baseUrl)
        Log.d("temp", tempUrl)
        var emuUrl = getString(R.string.baseUrlDev)
        Log.d("emu", emuUrl)
        var retrofit = Retrofit.Builder()
            .baseUrl(emuUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService: LoginService = retrofit.create(LoginService::class.java)

        btn_login.setOnClickListener{
            var username = userName.text.toString()
            var password = userPassword.text.toString()
            Log.d("ID", username)
            Log.d("PW", password)
            loginService.requestLogin(username, password).enqueue(object: Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Log.d("CometChatAPI::", "Failed API call with call: " + call + " + exception: " + t)
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출 실패")
                    dialog.show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    login = response.body()
                    Log.d("LOGIN", "msg : "+login?.token)
                    Log.d("LOGIN", "msg : "+login?.user_pk)
                    Log.d("LOGIN", "msg : "+login?.error)
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("Login Result")
                    dialog.setMessage("받았엉")
                    dialog.show()
                }
            })
        }
    }
}