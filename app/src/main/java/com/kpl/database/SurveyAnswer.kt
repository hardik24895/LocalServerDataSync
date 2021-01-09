package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SurveyAnswer")
data class SurveyAnswer(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SurveyID") val SurveyID: Int?,
    @ColumnInfo(name = "QuestionID") val QuestionID: String?,
    @ColumnInfo(name = "Answer") val Answer: String?,

)