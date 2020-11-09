package com.example.mapmo.uicomponents.activities.landing

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapmo.MemoListActivity
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


    lateinit var mRecyclerView: RecyclerView
    lateinit var mNoteAdapter: NoteAdapter
    private lateinit var mNoteListViewModel: NoteListViewModel
    private lateinit var mNoteModel: NoteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        checkPermission()

        mRecyclerView = findViewById(R.id.listOfNoteRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mNoteAdapter = NoteAdapter(this,noteList = ArrayList<NoteModel>(),
            onClickListener = this,
            onDeletePressed = DeleteBtnClick(),
            onEditPressed = EditBtnClick()
        )
        mRecyclerView.adapter = mNoteAdapter

        fab.setOnClickListener { view ->
            invokeNewNoteActivity()
        }

        // 일 단위로 메모 보여주기로 이동
        btn_to_memoList.setOnClickListener{
            val memoListIntent = Intent(this, MemoListActivity::class.java)
            startActivity(memoListIntent)
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
            R.id.action_settings ->
                goToSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToSettings() {

        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
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

    val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    val PERM_LOCATION = 99

    private fun checkPermission() {
        var permitted_all = true
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permitted_all = false
                requestPermission()
                break
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, PERM_LOCATION)
    }

    private fun confirmAgain() {
        AlertDialog.Builder(this)
            .setTitle("권한 승인 확인")
            .setMessage("위치 관련 권한을 모두 승인하셔야 앱을 사용할 수 있습니다. 권한 스인을 다시 하시겠습니까?")
            .setPositiveButton("네", { _, _-> requestPermission()})
            .setNegativeButton("아니요", { _, _-> finish()})
            .create()
            .show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            99 -> {
                var granted_all = true
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        granted_all = false
                        break
                    }
                }
                if (granted_all) {
                    Log.e("success", "정상")
                } else {
                    confirmAgain()
                }
            }
        }

    }
}
