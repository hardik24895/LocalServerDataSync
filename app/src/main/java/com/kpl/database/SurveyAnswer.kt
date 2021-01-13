package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kpl.utils.Constant

@Entity(tableName = Constant.TABLE_SURVEY_ANSWER)
data class SurveyAnswer(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SurveyAnswerID") val SurveyAnswerID: Long? = null,
    @ColumnInfo(name = "SurveyID") var SurveyID: Int,
    @ColumnInfo(name = "QuestionID") val QuestionID: String?,
    @ColumnInfo(name = "Answer") val Answer: String?,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: String?,
    @ColumnInfo(name = "CreatedDate") val CreatedDate: String?,
    @ColumnInfo(name = "ModifiedBy") val ModifiedBy: String?,
    @ColumnInfo(name = "ModifiedDate") val ModifiedDate: String?,
    @ColumnInfo(name = "Status") val Status: String?

)




