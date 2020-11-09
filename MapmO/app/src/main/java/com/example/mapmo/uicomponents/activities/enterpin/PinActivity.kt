package com.example.mapmo.uicomponents.activities.enterpin

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager

import com.example.mapmo.uicomponents.base.BaseActivity
import com.example.mapmo.uicomponents.common.PinEntryEditText
import kotlinx.android.synthetic.main.activity_note.*
import android.text.Editable
import android.text.TextWatcher
import com.example.mapmo.R
import android.view.View
import com.example.mapmo.common.Constants
import com.example.mapmo.uicomponents.activities.landing.MainActivity
import com.example.mapmo.uicomponents.activities.settings.SecurityResolutionDialog


class PinActivity : BaseActivity(), View.OnClickListener , SecurityResolutionDialog.UserEnteredAnswer {
    lateinit var mOtpEditText : PinEntryEditText
    lateinit var mInputMethodManager : InputMethodManager
    /*lateinit var mSubmit : Button*/
    val resultIntent = Intent()
    var mPreviousPIN = -1
    lateinit var mPreviousQSTN : String
    lateinit var mPreviousANSWER : String
    var mWrongTrialCount = 0
    val TAG = "PinActivity"
    lateinit var mSubmittedPIN : String
    lateinit var mSecretQstnDialog : SecurityResolutionDialog
    var mShouldNavigateToHome = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.enter_pin)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mPreviousPIN = intent.getIntExtra(Constants.LAST_PIN,-1)
        mPreviousQSTN = intent.getStringExtra(Constants.QUESTION).toString()
        mPreviousANSWER = intent.getStringExtra(Constants.ANSWER).toString()
        mShouldNavigateToHome = intent.getBooleanExtra(Constants.NAVIGATE_TO_HOME,false)
        mSecretQstnDialog =  SecurityResolutionDialog(mPreviousQSTN,mPreviousANSWER,this)
        mAppLogger.debug(TAG,"mPreviousPIN === $mPreviousPIN")
        mAppLogger.debug(TAG,"mPreviousANSWER === $mPreviousANSWER")

        /*mSubmit = findViewById(R.id.btnSubmit)
        mSubmit.setOnClickListener(this)*/
        mSubmittedPIN = ""
        mOtpEditText = findViewById(R.id.otpView)
        mOtpEditText.requestFocus()
        mInputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        mOtpEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence,
                                           start: Int,
                                           count: Int,
                                           after: Int) {
            }

            override fun onTextChanged(s: CharSequence,
                                       start: Int,
                                       before: Int,
                                       count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 4) {
                    mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                    //sendDataBack(s.toString())
                    //compareData(s.toString())
                    mSubmittedPIN = s.toString()
                    compareData(mSubmittedPIN)
                }
            }
        })
    }


    override fun onClick(v: View?) {
        /*if(v!!.id == R.id.btnSubmit){

            compareData(mSubmittedPIN)

        }*/
    }

    fun compareData(data:String){
        if(!data.isEmpty()){
            if(mPreviousPIN.compareTo(data.toInt())==0){
                //PIN Success
                //showToast("Success")
                userEnteredCorrectInput(true)
            }else {
                mWrongTrialCount ++

                if(mWrongTrialCount < 3)
                {
                    showToast("비밀번호가 일치하지 않습니다. 다시시도해주세요!")
                    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    mOtpEditText.requestFocus()
                    try {
                        mOtpEditText.text!!.clear()
                    } catch (e: Exception) {

                    }
                }
            }

            if(mWrongTrialCount >= 3){
                //showToast("Three times wrong data")
                //show the dialog with forgot password option
                mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                val fragmentTransitionImpl = supportFragmentManager.beginTransaction()
                val previousInstance = supportFragmentManager.findFragmentByTag("QSTNFRAG")
                if(previousInstance != null){
                    fragmentTransitionImpl.remove(previousInstance)
                }
                fragmentTransitionImpl.addToBackStack(null)

                mSecretQstnDialog.show(fragmentTransitionImpl,"QSTNFRAG")
            }
        }
    }
/*
    fun sendDataBack(data:String){

        if(!data.isEmpty()){
            resultIntent.putExtra(PIN_ENTERED,data.toInt())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }*/

    override fun userEnteredCorrectInput(status:Boolean) {
        if(status){

            if(mShouldNavigateToHome){
                startActivity(Intent(this@PinActivity , MainActivity::class.java))
                finish()
            }else{
                resultIntent.putExtra(PIN_ENTERED_STATUS,status)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    companion object {
        val PIN_ENTERED_STATUS = "USER_PIN"
    }

    override fun onSupportNavigateUp(): Boolean {
        mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        setResult(RESULT_CANCELED)
        finish()
        return true
    }
}