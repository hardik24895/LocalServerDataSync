package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpl.utils.Constant

@Dao
interface CategoryDao {


    @Query("SELECT * FROM " + Constant.TABLE_CATEGORY)
    fun getAllCategory(): List<Category>
//
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCategory(category: List<Category>)


}