package com.kpl.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kpl.utils.Constant.DATABASE_NAME

@Database(
    entities = arrayOf(Question::class, Survey::class, SurveyAnswer::class, Employee::class, Project::class),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    abstract fun surveyDao(): SurveyDao

    abstract fun surveyAnswerDao(): SurveyAnswerDao

    abstract fun employeeDao(): EmployeeDao

    abstract fun projectDao(): ProjectDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase::class.java, DATABASE_NAME
                    ).build()
                }
            }

            return INSTANCE
        }
    }

}