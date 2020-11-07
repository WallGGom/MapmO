package com.example.calendartest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orm_week")
class WeekRoom {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var year: Int = 0

    @ColumnInfo
    var month: Int = 0

    @ColumnInfo
    var weekList: String = "[0,0,0,0,0,0,0]"

    constructor(year: Int, month: Int, weekList: String) {
        this.year = year
        this.month = month
        this.weekList = weekList

    }
}