package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kpl.utils.Constant

@Entity(tableName = Constant.TABLE_SURVEY)
data class Survey(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.SurveyID) var SurveyID: Int?,
    @ColumnInfo(name = Constant.ProjectID) var ProjectID: Int,
    @ColumnInfo(name = Constant.Title) var Title: String?,
    @ColumnInfo(name = Constant.SurveyDate) var SurveyDate: String?,
    @ColumnInfo(name = Constant.UserID) var UserID: String?,
    @ColumnInfo(name = Constant.CreatedBy) var CreatedBy: String?,
    @ColumnInfo(name = Constant.CreatedDate) var CreatedDate: String?,
    @ColumnInfo(name = Constant.ModifiedBy) var ModifiedBy: String?,
    @ColumnInfo(name = Constant.ModifiedDate) var ModifiedDate: String?,
    @ColumnInfo(name = Constant.Status) var Status: String?
)