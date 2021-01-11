package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kpl.utils.Constant

@Dao
interface ProjectDao {


//    @Query("SELECT * FROM " + Constant.TABLE_PROJECT)
//    fun getAllProject(): List<Project>


//    @Insert
//    fun insertProject(project: Project)
//
    @Insert
    fun insertAllProject(project: ArrayList<Project>)



}