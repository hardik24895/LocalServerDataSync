package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Survey")
data class Survey(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SurveyID") val SurveyID: Int?,
    @ColumnInfo(name = "ProjectId") val ProjectId: String?,
    @ColumnInfo(name = "Title") val Title: String?,
    @ColumnInfo(name = "CreatedDate") val CreatedDate: String?,

)