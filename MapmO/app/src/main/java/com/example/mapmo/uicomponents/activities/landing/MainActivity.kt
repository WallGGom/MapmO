package com.example.mapmo.uicomponents.activities.landing

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mapmo.MemoMonthActivity
import com.example.mapmo.MemoWeekActivity
import com.example.mapmo.R
import com.example.mapmo.common.Constants
import com.example.mapmo.models.NoteListViewModel
import com.example.mapmo.models.NoteModel
import com.example.mapmo.uicomponents.activities.makenote.MakeNoteActivity
import com.example.mapmo.uicomponents.activities.settings.SettingsActivity
import com.example.mapmo.uicomponents.activities.viewnote.ViewNote
import com.example.mapmo.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {


    //    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
//    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val LOCATION_PERMISSION = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    val temp =  arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.RECORD_AUDIO)


    val FLAG_PERM_LOCATION = 97

    // 권한 플래그값 정의
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_STORAGE = 102

    lateinit var mRecyclerView: RecyclerView
    lateinit var mNoteAdapter: NoteAdapter
    private lateinit var mNoteListViewModel: NoteListViewModel
    private lateinit var mNoteModel: NoteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
//
//        checkPermission(CAMERA_PERMISSION,FLAG_PERM_CAMERA)
//        checkPermission(STORAGE_PERMISSION,FLAG_PERM_STORAGE)
//        checkPermission(LOCATION_PERMISSION,FLAG_PERM_LOCATION)
        checkPermission(temp, 99)

        mRecyclerView = findViewById(R.id.listOfNoteRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mNoteAdapter = NoteAdapter(this, noteList = ArrayList<NoteModel>(),
                onClickListener = this,
                onDeletePressed = DeleteBtnClick(),
                onEditPressed = EditBtnClick()
        )
        mRecyclerView.adapter = mNoteAdapter

        fab.setOnClickListener { view ->
            invokeNewNoteActivity()
        }

        // 주 단위로 메모 보여주기로 이동
        btn_to_week.setOnClickListener{
            val memoDtW = Intent(this, MemoWeekActivity::class.java)
            startActivity(memoDtW)
        }

        // 월 단위로 메모 보여주기로 이동
        btn_to_month.setOnClickListener{
            val memoDtM = Intent(this, MemoMonthActivity::class.java)
            startActivity(memoDtM)
        }

        mNoteListViewModel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)
        mNoteListViewModel.mNoteList.observe(this@MainActivity, Observer { noteModels -> mNoteAdapter.addNote(noteModels) })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator = menuInflater
        inflator.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId)
        {
            R.id.month_to_day ->
                goToDay()
            R.id.action_settings ->
                goToSettings()
            R.id.month_to_month ->
                goToMonth()
            R.id.month_to_week ->
                goToWeek()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToSettings() {
        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
    }
    private fun goToMonth() {
        startActivity(Intent(this@MainActivity, MemoMonthActivity::class.java))
    }
    private fun goToWeek() {
        startActivity(Intent(this@MainActivity, MemoWeekActivity::class.java))
    }
    private fun goToDay() {
        startActivity(Intent(this@MainActivity, MainActivity::class.java))
    }


    private fun invokeNewNoteActivity() {
        val intent = Intent(this@MainActivity, MakeNoteActivity::class.java)
        startActivity(intent)
    }


    override fun onClick(v: View?) {
        mNoteModel = v?.getTag() as NoteModel
        val intent = Intent(this@MainActivity, ViewNote::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constants.SELECTED_NOTE,mNoteModel)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    override fun handlePositiveAlertCallBack() {
        mNoteListViewModel.deleteNote(mNoteModel)
        showToast(getString(R.string.note_deleted))
    }

    inner class DeleteBtnClick : View.OnClickListener{
        override fun onClick(v: View?) {
            mNoteModel = v?.getTag() as NoteModel
            showAlert(getString(R.string.delete_confirmation), R.string.ok, R.string.cancel)
        }
    }

    inner class EditBtnClick : View.OnClickListener{
        override fun onClick(v: View?) {
            mNoteModel = v?.getTag() as NoteModel
            val intent = Intent(this@MainActivity, MakeNoteActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(Constants.SELECTED_NOTE,mNoteModel)
            intent.putExtras(bundle)
            intent.putExtra(Constants.EDIT_ACTION,true)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



    private fun checkPermission(permissions: Array<out String>, flag: Int) {
        var permitted_all = true
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permitted_all = false
                requestPermission(permissions, flag)

            }
        }
    }

    private fun requestPermission(permissions: Array<out String>, flag: Int) {
        ActivityCompat.requestPermissions(this, permissions, flag)
    }

    private fun confirmAgain() {
        AlertDialog.Builder(this)
                .setTitle("권한 승인 확인")
                .setMessage("위치 관련 권한을 모두 승인하셔야 앱을 사용할 수 있습니다. 권한 승인을 다시 하시겠습니까?")
                .setPositiveButton("네") { _, _ -> requestPermission(LOCATION_PERMISSION, FLAG_PERM_LOCATION) }
                .setNegativeButton("아니요") { _, _ -> finish() }
                .create()
                .show()
    }

}