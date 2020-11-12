package com.example.mapmo.uicomponents.activities.viewnote

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.net.toUri
import com.example.mapmo.common.Constants
import com.example.mapmo.models.NoteModel
import com.example.mapmo.uicomponents.activities.makenote.MakeNoteActivity
import com.example.mapmo.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_make_note.*
import kotlinx.android.synthetic.main.content_view_note.*
import java.lang.Exception

class ViewNote : BaseActivity() {

    private var mNoteModel: NoteModel? = null
    var textNoteHead : TextView? = null
    var textNoteDesc : TextView? = null
    var linearBackground : LinearLayoutCompat? = null

    var textPlace: TextView? = null
    var textPlanDate: TextView? = null
    var textPlanTime: TextView? = null
    var textAlarmChecked: TextView? = null
    var textAlarmTime: TextView? = null

    var textLatitude: TextView? = null
    var textLongitude: TextView? = null
    var imageUri: Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mapmo.R.layout.activity_view_note)
        val actionBar = toolbar
        actionBar!!.title = getString(com.example.mapmo.R.string.view_note)
        setSupportActionBar(actionBar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        linearBackground = findViewById(com.example.mapmo.R.id.readView)

        mNoteModel = intent.extras!!.getSerializable(Constants.SELECTED_NOTE) as NoteModel

        // 데이터 넣기
        val noteTitle = mNoteModel?.noteTitle
        val noteDesc = mNoteModel?.noteDescription
        val notePlace = mNoteModel?.place
        val notePlanDate = mNoteModel?.planDate
        val notePlanTime = mNoteModel?.planTime
        val noteAlarmChecked = mNoteModel?.alarmCheck
        val noteAlarmTime = mNoteModel?.alarmSettime
        val noteImage = mNoteModel?.image

        val noteLatitude = mNoteModel?.latitude
        val noteLongitude = mNoteModel?.longitude

        // 변수 = ID 매핑
        textNoteHead = findViewById(com.example.mapmo.R.id.noteHead)
        textNoteDesc = findViewById(com.example.mapmo.R.id.noteContent)

        textPlace = findViewById(com.example.mapmo.R.id.readPlace)

        textPlanDate = findViewById(com.example.mapmo.R.id.readDateTv)
        textPlanTime = findViewById(com.example.mapmo.R.id.readTimeTv)

        textAlarmChecked = findViewById(com.example.mapmo.R.id.readAlarmCheck)
        textAlarmTime = findViewById(com.example.mapmo.R.id.readAlarmTime)
//        textImageView = findViewById(com.example.mapmo.R.id.readImageView)


        // 이미 작성된 메모
        if (mNoteModel!=null && !TextUtils.isEmpty(noteTitle)){

            // 메모제목 입력
            textNoteHead?.text = noteTitle

            // 메모내용(Description) 입력
            if (!TextUtils.isEmpty(noteDesc)) {
                textNoteDesc?.text = noteDesc
            }

            if (!TextUtils.isEmpty(notePlace)) {
                textPlace?.text = notePlace
            }
            if (!TextUtils.isEmpty(notePlanDate)) {
                textPlanDate?.text = notePlanDate
            }

            textLongitude?.text = noteLongitude.toString()
            textLatitude?.text = noteLatitude.toString()

            if (!TextUtils.isEmpty(notePlanTime)) {
                textPlanTime?.text = notePlanTime
                textAlarmTime?.text = noteAlarmTime
                if (noteAlarmChecked == true) {
                    textAlarmChecked?.text = "On"
                } else {
                    textAlarmChecked?.text = "Off"
                }
            } else {
                textPlanDate?.text = "메모 일정이 없습니다."
                textViewAlarm.text = ""
                textAlarmChecked?.text = ""
                textAlarmTime?.text = ""

            }
            // 메모 색상 입력
            val background = mNoteModel!!.noteColor

            // 메모 색상이 -1이면 작업~
            if(background != -1){

                textNoteHead!!.setTextColor(resources.getColor(R.color.white))
                textNoteDesc!!.setTextColor(resources.getColor(R.color.white))

                when(background){
                    1 ->
                        linearBackground!!.setBackgroundColor(resources.getColor(R.color.holo_red_light))
                    2 ->
                        linearBackground!!.setBackgroundColor(resources.getColor(R.color.holo_green_light))
                    3 ->
                        linearBackground!!.setBackgroundColor(resources.getColor(R.color.holo_blue_light))
                    4 ->
                        linearBackground!!.setBackgroundColor(resources.getColor(R.color.holo_orange_light))

                    else -> {
                        textNoteHead!!.setTextColor(resources.getColor(R.color.black))
                        textNoteDesc!!.setTextColor(resources.getColor(R.color.darker_gray))
                    }
                }
            }
            if (mNoteModel?.image != null) {
                val uri_temp = mNoteModel?.image!!.toUri()
                imageUri = uri_temp
                Log.e("uriInView", imageUri.toString())
                readImageView.setImageURI(imageUri)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator = menuInflater
        inflator.inflate(com.example.mapmo.R.menu.edit_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId)
        {
            com.example.mapmo.R.id.action_edit ->
            {
                showEditNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showEditNote() {
        //first close this view
        finish()
        //open new edit activity
        val intent = Intent(this@ViewNote, MakeNoteActivity::class.java)
        intent.putExtra(Constants.SELECTED_NOTE,mNoteModel)
        intent.putExtra(Constants.EDIT_ACTION,true)
        startActivity(intent)
    }
}
