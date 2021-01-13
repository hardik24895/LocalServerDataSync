package com.kpl.database

import android.provider.SyncStateContract
import androidx.room.*
import com.kpl.utils.Constant
import org.jetbrains.annotations.NotNull

@Dao
interface SurveyAnswerDao {

    @Query("SELECT * FROM " + Constant.TABLE_SURVEY_ANSWER)
    fun getAll(): List<SurveyAnswer>


    @Query("SELECT * FROM  ${Constant.TABLE_SURVEY_ANSWER} Where ${Constant.SurveyID} = :surveyID and ${Constant.QuestionID} = :questionID ")
    fun checkRecordExist(surveyID: String, questionID: String): SurveyAnswer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(surveyAnswer: SurveyAnswer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(surveyAnswer: ArrayList<SurveyAnswer>)

    @Query("Update ${Constant.TABLE_SURVEY_ANSWER} set Answer = :answer  Where ${Constant.SurveyID} = :surveyID and ${Constant.QuestionID} = :questionID")
    fun updaterecord(surveyID: String, questionID: String, answer: String)

}