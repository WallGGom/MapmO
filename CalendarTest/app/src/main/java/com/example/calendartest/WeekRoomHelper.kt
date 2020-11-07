package com.example.calendartest

import androidx.room.Database
import androidx.room.RoomDatabase

// version : DB버전을 정의 | exportSchema : Room에 스키마를 폴더로 내보내도록 설정여부 정하기
@Database(entities = arrayOf(WeekRoom::class), version = 1, exportSchema = false)
abstract class WeekRoomHelper : RoomDatabase() {
    abstract fun weekRoomDao() : WeekRoomDao
}