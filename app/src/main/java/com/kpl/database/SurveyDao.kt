package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpl.utils.Constant
import org.jetbrains.annotations.NotNull

@Dao
interface SurveyDao {

    @Query("SELECT * FROM " + Constant.TABLE_SURVEY)
    fun getAllSurvey(): List<Survey>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSurvey(survey: Survey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSurvey(survey: ArrayList<Survey>)


}