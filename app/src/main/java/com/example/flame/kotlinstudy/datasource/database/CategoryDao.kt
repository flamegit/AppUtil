package com.example.flame.kotlinstudy.datasource.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.example.flame.kotlinstudy.model.Category

@Dao
interface CategoryDao {

    @Query("select * from category")
    fun getAllCategory(): List<Category>

    @Query("select * from category where id = :id")
    fun findCategoryById(id: Long): Category

    @Insert(onConflict = REPLACE)
    fun insertCategory(category: Category)

    @Update(onConflict = REPLACE)
    fun updateCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)
}