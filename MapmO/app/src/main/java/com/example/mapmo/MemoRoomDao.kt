package com.example.mapmo

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface MemoRoomDao {
    @Query("select * from orm_memo")
    fun getAll(): MutableList<MemoRoom>
//    @Query("select * from orm_memo WHERE alarmdate = :stdDate")
//    fun getAllInDay(stdDate: String): List<MemoRoom>
//    @Query("SELECT * FROM orm_memo WHERE alarmdate BETWEEN :startDate AND :endDate")
//    fun getAllBetweenWeek(startDate: String, endDate: String): List<MemoRoom>
//    @Query("SELECT * FROM orm_memo WHERE alarmdate BETWEEN :startDate AND :endDate")
//    fun getAllBetweenMonth(startDate: String, endDate: String): List<MemoRoom>
    @Insert(onConflict = REPLACE)
    fun insert(memo: MemoRoom)
    @Delete
    fun delete(memo: MemoRoom)
    @Update
    fun update(memo: MemoRoom)
}