package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpl.utils.Constant

@Dao
interface QuestionDao {


    @Query("SELECT * FROM " + Constant.TABLE_QUESTION)
    fun getAllQuestion(): List<Question>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllQuestion(question: ArrayList<Question>)

//    @Delete
//    suspend fun delete(user: Question)

}