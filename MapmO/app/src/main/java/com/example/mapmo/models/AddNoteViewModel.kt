package com.example.mapmo.models

import android.app.Application
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import com.example.mapmo.db.DBUtils
import com.example.mapmo.db.NoteDataBase

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private var mNoteDataBase: NoteDataBase = NoteDataBase.getInstance(getApplication())!!

    fun addNote(noteModel: NoteModel){
        var dbUtils= DBUtils()
        Log.e("addNote", noteModel.image)
        Log.e("addNote", "${noteModel.image.toUri()}")
        dbUtils.addNote(mNoteDataBase,noteModel)
    }
}