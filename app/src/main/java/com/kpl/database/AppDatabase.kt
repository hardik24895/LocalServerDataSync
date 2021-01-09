package com.kpl.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Question::class, Survey::class, SurveyAnswer::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    abstract fun surveyDao(): SurveyDao

    abstract fun surveyAnswerDao(): SurveyAnswerDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase::class.java, "survey.db"
                    ).build()
                }
            }

            return INSTANCE
        }
    }

}