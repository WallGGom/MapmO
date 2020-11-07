package com.example.calendartest

import androidx.room.*

@Dao
interface WeekRoomDao {
    @Query("select * from orm_week")
    fun getAll(): MutableList<WeekRoom>
    //    @Query("select * from orm_memo WHERE alarmdate = :stdDate")
//    fun getAllInDay(stdDate: String): List<MemoRoom>
//    @Query("SELECT * FROM orm_memo WHERE alarmdate BETWEEN :startDate AND :endDate")
//    fun getAllBetweenWeek(startDate: String, endDate: String): List<MemoRoom>
//    @Query("SELECT * FROM orm_memo WHERE alarmdate BETWEEN :startDate AND :endDate")
//    fun getAllBetweenMonth(startDate: String, endDate: String): List<MemoRoom>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(week: WeekRoom)
    @Delete
    fun delete(week: WeekRoom)
    @Update
    fun update(week: WeekRoom)
}