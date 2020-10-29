package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface MemoRoomDao {
    @Query("select * from orm_memo")
    fun getAll(): List<MemoRoom>
    @Insert(onConflict = REPLACE)
    fun insert(memo: MemoRoom)
    @Delete
    fun delete(memo: MemoRoom)
}