package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpl.utils.Constant

@Dao
interface ProjectDao {


    @Query("SELECT * FROM " + Constant.TABLE_PROJECT)
    fun getAllProject(): List<Project>

    @Query("SELECT * FROM  ${Constant.TABLE_PROJECT} WHERE ${Constant.ProjectID}  = :ProjectId")
    fun getProjectData(ProjectId: Int): Project


    //    @Insert
//    fun insertProject(project: Project)
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProject(project: ArrayList<Project>)


}