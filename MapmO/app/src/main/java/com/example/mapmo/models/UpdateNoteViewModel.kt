package com.example.mapmo.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mapmo.db.DBUtils
import com.example.mapmo.db.NoteDataBase

class UpdateNoteViewModel(application: Application) : AndroidViewModel(application) {

    private var mNoteDataBase: NoteDataBase = NoteDataBase.getInstance(getApplication())!!

    fun updateNote(noteModel: NoteModel ){
        var dbUtils= DBUtils()
        dbUtils.updateNote(mNoteDataBase,noteModel)
    }
}