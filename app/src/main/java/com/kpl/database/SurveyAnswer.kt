package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kpl.utils.Constant

@Entity(tableName = Constant.TABLE_SURVEY_ANSWER)
data class SurveyAnswer(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.SurveyAnswerID) val SurveyAnswerID: Long? = null,
    @ColumnInfo(name = Constant.SurveyID) var SurveyID: Int,
    @ColumnInfo(name = Constant.QuestionID) val QuestionID: String?,
    @ColumnInfo(name = Constant.Answer) val Answer: String?,
    @ColumnInfo(name = Constant.Image) var Image: String?,
    @ColumnInfo(name = Constant.CreatedBy) val CreatedBy: String?,
    @ColumnInfo(name = Constant.CreatedDate) val CreatedDate: String?,
    @ColumnInfo(name = Constant.ModifiedBy) val ModifiedBy: String?,
    @ColumnInfo(name = Constant.ModifiedDate) val ModifiedDate: String?,
    @ColumnInfo(name = Constant.Status) val Status: String?

)




