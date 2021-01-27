package com.kpl.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kpl.utils.Constant


@Entity(tableName = Constant.TABLE_USER)

data class Employee(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.UserID) val UserID: Int? = null,
    @ColumnInfo(name = Constant.RoleID) val RoleID: Int? = null,
    @ColumnInfo(name = Constant.EmailID) val EmailID: String? = null,
    @ColumnInfo(name = Constant.Password) val Password: String? = null,
    @ColumnInfo(name = Constant.FirstName) val FirstName: String? = null,
    @ColumnInfo(name = Constant.LastName) val LastName: String? = null,
    @ColumnInfo(name = Constant.MobileNo) val MobileNo: String? = null,
    @ColumnInfo(name = Constant.Address) val Address: String? = null,
    @ColumnInfo(name = Constant.UserType) val UserType: String? = null,
    @ColumnInfo(name = Constant.IsDeleted) val IsDeleted: String? = null,
    @ColumnInfo(name = Constant.CreatedBy) val CreatedBy: String? = null,
    @ColumnInfo(name = Constant.CreatedDate) val CreatedDate: String? = null,
    @ColumnInfo(name = Constant.ModifiedBy) val ModifiedBy: String? = null,
    @ColumnInfo(name = Constant.ModifiedDate) val ModifiedDate: String? = null,
    @ColumnInfo(name = Constant.Status) val Status: String? = null
)