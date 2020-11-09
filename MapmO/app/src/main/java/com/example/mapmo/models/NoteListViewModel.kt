package com.example.mapmo.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mapmo.db.DBUtils
import com.example.mapmo.db.NoteDataBase

class NoteListViewModel(application: Application):  AndroidViewModel(application) {

    var mNoteList: LiveData<List<NoteModel>>
    private var mNoteDataBase: NoteDataBase = NoteDataBase.getInstance(getApplication())!!

    init {
        mNoteList = mNoteDataBase.noteItemAndNotesModel().getAllNotes()
    }

    fun deleteNote(noteModel: NoteModel){
        val dbUtils = DBUtils()
        dbUtils.deleteNoteItem(mNoteDataBase,noteModel)
    }
}