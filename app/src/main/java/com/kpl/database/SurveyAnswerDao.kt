package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpl.utils.Constant

@Dao
interface SurveyAnswerDao {

    @Query("SELECT * FROM " + Constant.TABLE_SURVEY_ANSWER)
    fun getAll(): List<SurveyAnswer>


    @Query("SELECT * FROM  ${Constant.TABLE_SURVEY_ANSWER} Where ${Constant.SurveyID} = :surveyID and ${Constant.QuestionID} = :questionID ")
    fun checkRecordExist(surveyID: Int, questionID: String): SurveyAnswer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(surveyAnswer: SurveyAnswer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(surveyAnswer: ArrayList<SurveyAnswer>)

    @Query("Update ${Constant.TABLE_SURVEY_ANSWER} set Answer = :answer  Where ${Constant.SurveyID} = :surveyID and ${Constant.QuestionID} = :questionID")
    fun updaterecord(surveyID: Int, questionID: String, answer: String)

    @Query("Update ${Constant.TABLE_SURVEY_ANSWER} set ${Constant.SurveyID} = :surveyId  Where ${Constant.SurveyID} = -1 ")
    fun updaterecord( surveyId: Int)

    @Query("DELETE FROM " + Constant.TABLE_SURVEY_ANSWER)
    fun deleteAllReocord()

}