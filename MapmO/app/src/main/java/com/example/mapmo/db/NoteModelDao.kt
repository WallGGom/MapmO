package com.example.mapmo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mapmo.models.NoteModel
import com.example.mapmo.models.SecurityQuestionModel

@Dao
interface NoteModelDao {

    @Query("SELECT * FROM NoteModel ORDER BY createdAt DESC")
    fun getAllNotes(): LiveData<List<NoteModel>>

    @Query("SELECT * FROM NoteModel ORDER BY createdAt DESC")
    fun getAll(): MutableList<NoteModel>

    @Query("select * from NoteModel WHERE (planDate like :stdDate) OR (createdAt like :stdDate)")
    fun getAllInDay(stdDate: String): MutableList<NoteModel>

    @Query("SELECT * FROM NoteModel WHERE (planDate BETWEEN :startDate AND :endDate) OR (createdAt BETWEEN :startDate AND :endDate)")
    fun getAllBetweenWeek(startDate: String, endDate: String): MutableList<NoteModel>

    @Query("SELECT * FROM NoteModel WHERE (planDate BETWEEN :startDate AND :endDate) OR (createdAt BETWEEN :startDate AND :endDate)")
    fun getAllBetweenMonth(startDate: String, endDate: String): List<NoteModel>

    @Query("SELECT * FROM NoteModel WHERE id = :id")
    fun getNoteById(id: String ):NoteModel

    @Insert
    fun insertNote(noteModel: NoteModel)

    @Delete
    fun deleteNote(noteModel: NoteModel)

    @Update
    fun updateNote(noteModel: NoteModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateSecurityQuestion(securityQuestionModel: SecurityQuestionModel)

    @Query("SELECT * FROM SecurityQuestionModel")
    fun getSecurityQuestion(): SecurityQuestionModel

}



