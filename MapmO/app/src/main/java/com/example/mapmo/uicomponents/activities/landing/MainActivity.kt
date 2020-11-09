package com.example.mapmo.uicomponents.activities.landing

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
}
