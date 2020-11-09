package com.example.mapmo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class NoteModel(@PrimaryKey(autoGenerate = true)
                     var id:Int?,
                     var noteTitle:String,
                     var noteDescription:String,
                     var dateSaved: Date?,
                     var noteColor: Int,
                     var planDate: String,
                     var planTime: String,
                     var alarmCheck: Boolean,
                     var alarmSettime: String
                     ) : Serializable
