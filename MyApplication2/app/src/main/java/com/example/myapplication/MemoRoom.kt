package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orm_memo")
class MemoRoom {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no:Long?=null
    @ColumnInfo
    var title:String=""
    @ColumnInfo
    var content:String=""
    @ColumnInfo
    var place:String=""
    @ColumnInfo(name="date")
    var datetime:Long=0

    constructor(title:String, content:String, place:String, datetime:Long) {
        this.title = title
        this.content = content
        this.place = place
        this.datetime = datetime

    }
}