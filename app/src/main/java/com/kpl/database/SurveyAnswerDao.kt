package com.kpl.database

import android.provider.SyncStateContract
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kpl.utils.Constant
import org.jetbrains.annotations.NotNull

@Dao
interface SurveyAnswerDao {

    @Query("SELECT * FROM " + Constant.TABLE_SURVEY_ANSWER)
    fun getAll(): List<SurveyAnswer>

    @Insert
    fun insert(surveyAnswer: SurveyAnswer)

    @Insert
    fun insertAll(surveyAnswer: ArrayList<SurveyAnswer>)
//
//    @Query("UPDATE ${Constant.TABLE_SURVEY_ANSWER} SET ${Constant.QuestionID} = :isFavorite WHERE name = :name")
//    fun setFavourite(name: String, isFavorite: Int)


}