package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kpl.utils.Constant

@Entity(tableName = Constant.TABLE_SURVEY)
data class Survey(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.SurveyID) val SurveyID: Int?,
    @ColumnInfo(name = Constant.ProjectId) val ProjectId: Int?,
    @ColumnInfo(name = Constant.Title) val Title: String?,
    @ColumnInfo(name = Constant.Date) val Date: String?,
    @ColumnInfo(name = Constant.UserID) val UserID: Int?,
    @ColumnInfo(name = Constant.CreatedBy) val CreatedBy: String?,
    @ColumnInfo(name = Constant.CreatedDate) val CreatedDate: String?,
    @ColumnInfo(name = Constant.ModifiedBy) val ModifiedBy: String?,
    @ColumnInfo(name = Constant.ModifiedDate) val ModifiedDate: String?,
    @ColumnInfo(name = Constant.Status) val Status: String?


)