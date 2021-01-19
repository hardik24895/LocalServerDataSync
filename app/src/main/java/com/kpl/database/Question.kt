package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kpl.utils.Constant

@Entity(tableName = Constant.TABLE_QUESTION)
data class Question(

    @PrimaryKey(autoGenerate = true)
    //val id: Int? = null,
    //@ColumnInfo(name = "ID") val id: Int,
    @ColumnInfo(name = Constant.QuestionID) val QuestionID: Int?,
    @ColumnInfo(name = Constant.Question) val Question: String?,
    @ColumnInfo(name = Constant.Questionoption) val Questionoption: String?,
    @ColumnInfo(name = Constant.Type) val Type: String?,
    @ColumnInfo(name = Constant.CreatedBy) val CreatedBy: String?,
    @ColumnInfo(name = Constant.CreatedDate) val CreatedDate: String?,
    @ColumnInfo(name = Constant.ModifiedBy) val ModifiedBy: String?,
    @ColumnInfo(name = Constant.ModifiedDate) val ModifiedDate: String?,
    @ColumnInfo(name = Constant.Status) val Status: String?

)