package com.example.mapmo.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.mapmo.db.DBUtils
import com.example.mapmo.db.NoteDataBase
import com.example.mapmo.uicomponents.activities.settings.SettingsActivity
import com.example.mapmo.uicomponents.activities.setuppin.IGetSecurityQuestionListener
import com.example.mapmo.uicomponents.activities.setuppin.IUpdateSecurityListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecurityQuestionViewModel(application: Application):  AndroidViewModel(application) {

    private var mNoteDataBase: NoteDataBase = NoteDataBase.getInstance(getApplication())!!

    var dbUtils= DBUtils()

    fun insertOrUpdateSecurityQuestion(securityQuestionModel: SecurityQuestionModel,listener: IUpdateSecurityListener){

        dbUtils.setOrUpdateSecurityQstn(mNoteDataBase,securityQuestionModel,listener)

    }

    fun getSecurityQuestion(listenerIGet: IGetSecurityQuestionListener, operation: SettingsActivity.OPERATION){

        dbUtils.fetchSecurityQuestion(mNoteDataBase,listenerIGet,operation)

    }
}