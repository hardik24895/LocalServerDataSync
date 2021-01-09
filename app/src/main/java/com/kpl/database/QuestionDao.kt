package com.kpl.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionDao {



    @Query("SELECT * FROM Question")
    fun getAllQuestion(): List<Question>


    @Insert
    fun insertQuestion(question: Question)

    @Insert
    fun insertAllQuestion(question: ArrayList<Question>)

//    @Delete
//    suspend fun delete(user: Question)

}