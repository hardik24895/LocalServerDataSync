package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.kpl.utils.Constant
import org.jetbrains.annotations.NotNull

@Dao
interface EmployeeDao {


    @Query("SELECT * FROM " + Constant.TABLE_USER)
    fun getAllUsers(): List<Employee>

    // @Query("SELECT * FROM " + Constant.TABLE_USER + " WHERE " + Constant.MobileNo + " = "+:contact+"")
    @NotNull
    @Query("SELECT * FROM " + Constant.TABLE_USER + " WHERE " + Constant.MobileNo + " = :contact AND " + Constant.Password + " = :password")
    fun checkUser(contact: String, password: String): Employee


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(employee: Employee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUser(employee: List<Employee>)

//    @Delete
//    suspend fun delete(user: User)


}