package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Question")
data class Question(

    @PrimaryKey(autoGenerate = true)
    //val id: Int? = null,
    //@ColumnInfo(name = "ID") val id: Int,
    @ColumnInfo(name = "QuestionID") val QuestionID: Int?,
    @ColumnInfo(name = "Question") val Question: String?,
    @ColumnInfo(name = "Type") val Type: String?,
    @ColumnInfo(name = "Answer") val Answer: String?,
    @ColumnInfo(name = "CreatedBy") val CreatedBy: String?,
    @ColumnInfo(name = "CreatedDate") val CreatedDate: String?,
    @ColumnInfo(name = "ModifiedBy") val ModifiedBy: String?,
    @ColumnInfo(name = "ModifiedDate") val ModifiedDate: String?,
    @ColumnInfo(name = "Status") val Status: String?




)