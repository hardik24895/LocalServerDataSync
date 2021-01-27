package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kpl.utils.Constant

@Entity(tableName = Constant.TABLE_PROJECT)
data class Project(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.ProjectID) val ProjectID: Int?,
    @ColumnInfo(name = Constant.CompanyName) val CompanyName: String?,
    @ColumnInfo(name = Constant.Title) val Title: String?,
    @ColumnInfo(name = Constant.Address) val Address: String?,
    @ColumnInfo(name = Constant.MobileNo) val MobileNo: String?,
    @ColumnInfo(name = Constant.Type) val Type: String?,
    @ColumnInfo(name = Constant.Status) val Status: String?,
    @ColumnInfo(name = Constant.CreatedBy) val CreatedBy: String?,
    @ColumnInfo(name = Constant.CreatedDate) val CreatedDate: String?,
    @ColumnInfo(name = Constant.ModifiedBy) val ModifiedBy: String?,
    @ColumnInfo(name = Constant.ModifiedDate) val ModifiedDate: String
)

