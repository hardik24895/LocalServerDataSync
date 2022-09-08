package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kpl.utils.Constant

@Entity(tableName = Constant.TABLE_CATEGORY)
data class Category(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.CategoryID) var CategoryID: Int?,
    @ColumnInfo(name = Constant.ParentID) var ParentID: Int?,
    @ColumnInfo(name = Constant.Category) var Category: String,
    @ColumnInfo(name = Constant.CreatedBy) var CreatedBy: String?,
    @ColumnInfo(name = Constant.CreatedDate) var CreatedDate: String?,
    @ColumnInfo(name = Constant.ModifiedBy) var ModifiedBy: String?,
    @ColumnInfo(name = Constant.ModifiedDate) var ModifiedDate: String?,
    @ColumnInfo(name = Constant.Status) var Status: String?
)