package com.example.mapmo

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orm_memo")
class MemoRoom {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var title: String = ""

    @ColumnInfo
    var content: String = ""

    @ColumnInfo
    var place: String = ""

    @ColumnInfo
    var latitude: Long = 0

    @ColumnInfo
    var longitude: Long = 0

    @ColumnInfo(name = "date")
    var datetime: Long = 0

    // 알림날짜 / 알림시간 / 알림설정여부 / 알림설정시간 / 사진
    @ColumnInfo
    var alarmdate: String = ""

    @ColumnInfo
    var alarmtime: String = ""

    @ColumnInfo
    var alarmcheck: Boolean = false

    @ColumnInfo
    var alarmsettime: String = ""

//    @ColumnInfo
//    var photo: Uri? = null

    constructor(title: String, content: String, place: String, datetime: Long, latitude: Long, longitude: Long, alarmdate: String, alarmtime: String, alarmcheck: Boolean, alarmsettime: String ) {
        this.title = title
        this.content = content
        this.place = place
        this.datetime = datetime
        this.latitude = latitude
        this.longitude = longitude
        this.alarmdate = alarmdate
        this.alarmtime = alarmtime
        this.alarmcheck = alarmcheck
        this.alarmsettime = alarmsettime
//        this.photo = photo

    }
}