package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kpl.utils.Constant
import org.jetbrains.annotations.NotNull

@Dao
interface SurveyDao {

    @Query("SELECT * FROM " + Constant.TABLE_SURVEY)
    fun getAllSurvey(): List<Survey>

    @Insert
    fun insertSurvey(survey: Survey)

    @Insert
    fun insertAllSurvey(survey: ArrayList<Survey>)


}