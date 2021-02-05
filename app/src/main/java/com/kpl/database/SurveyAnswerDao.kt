package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpl.utils.Constant
import com.kpl.utils.Constant.SurveyAnswerID

@Dao
interface SurveyAnswerDao {

    @Query("SELECT * FROM " + Constant.TABLE_SURVEY_ANSWER)
    fun getAll(): List<SurveyAnswer>

    @Query("SELECT * FROM  ${Constant.TABLE_SURVEY_ANSWER}  Where ${Constant.Image} != :notnullImage And  ${Constant.Status} = 1")
    fun getAllImages(notnullImage: String): List<SurveyAnswer>

    @Query("SELECT * FROM  ${Constant.TABLE_SURVEY_ANSWER} Where ${Constant.SurveyID} = :surveyID and ${Constant.QuestionID} = :questionID ")
    fun checkRecordExist(surveyID: Int, questionID: String): SurveyAnswer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(surveyAnswer: SurveyAnswer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(surveyAnswer: ArrayList<SurveyAnswer>)

    @Query("Update ${Constant.TABLE_SURVEY_ANSWER} set Answer = :answer  Where ${Constant.SurveyID} = :surveyID and ${Constant.QuestionID} = :questionID")
    fun updaterecord(surveyID: Int, questionID: String, answer: String)

    @Query("Update ${Constant.TABLE_SURVEY_ANSWER} set ${Constant.SurveyID} = :surveyId  Where ${Constant.SurveyID} = -1 ")
    fun updaterecord(surveyId: Int)

    @Query("DELETE FROM " + Constant.TABLE_SURVEY_ANSWER)
    fun deleteAllReocord()

    @Query("Update ${Constant.TABLE_SURVEY_ANSWER} set Image = :imagepath  Where ${Constant.SurveyID} = :surveyID and ${Constant.QuestionID} = :questionID")
    fun updateImage(surveyID: Int, questionID: String, imagepath: String)

    @Query("Update ${Constant.TABLE_SURVEY_ANSWER} set ${Constant.Status} = '0'  Where ${Constant.SurveyAnswerID} = :surveyId")
    fun updateUploadedStatus(surveyId: Long?)


    @Query("SELECT ${Constant.Image} FROM ${Constant.TABLE_SURVEY_ANSWER} Where ${Constant.SurveyID} = :surveyID and ${Constant.QuestionID} = :questionID ")
    fun getImagePath(surveyID: Int, questionID: String): String

}