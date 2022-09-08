package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpl.utils.Constant
import org.jetbrains.annotations.NotNull

@Dao
interface SurveyDao {

    @Query("SELECT * FROM " + Constant.TABLE_SURVEY /*+ " Where ${Constant.Status} = 1 "*/)
    fun getAllSurvey(): List<Survey>

    @Query("SELECT * FROM " + Constant.TABLE_SURVEY + " Where ${Constant.Status} = 0")
    fun getAllPendingSurvey(): List<Survey>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSurvey(survey: Survey): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSurvey(survey: ArrayList<Survey>)


    @Query("Update ${Constant.TABLE_SURVEY} set ${Constant.Status} = 1 Where ${Constant.Status} = 0 ")
    fun uploadDataDone()

    @Query("DELETE FROM " + Constant.TABLE_SURVEY)
    fun deleteAllReocord()


}