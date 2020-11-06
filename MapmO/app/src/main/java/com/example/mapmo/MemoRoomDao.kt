package com.example.mapmo

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface MemoRoomDao {
    @Query("select * from orm_memo")
    fun getAll(): List<MemoRoom>

    @Insert(onConflict = REPLACE)
    fun insert(memo: MemoRoom)

    @Delete
    fun delete(memo: MemoRoom)

//    @Query("select * from orm_memo")
//    fun updateMemo(): List<MemoRoom>



}