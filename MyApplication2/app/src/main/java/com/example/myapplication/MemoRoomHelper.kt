package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MemoRoom::class), version = 1, exportSchema = false)
abstract class MemoRoomHelper : RoomDatabase() {
    abstract fun memoRoomDao() : MemoRoomDao
}