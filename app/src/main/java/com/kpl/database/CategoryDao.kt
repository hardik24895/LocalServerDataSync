package com.kpl.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kpl.utils.Constant

@Dao
interface CategoryDao {


    @Query("SELECT * FROM ${Constant.TABLE_CATEGORY} Where ${Constant.ParentID} = 0")
    fun getAllCategory(): List<Category>

    @Query("SELECT * FROM ${Constant.TABLE_CATEGORY} Where ${Constant.ParentID} = :categoryID")
    fun getSubCategory(categoryID: Int): List<Category>
//
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCategory(category: List<Category>)


}