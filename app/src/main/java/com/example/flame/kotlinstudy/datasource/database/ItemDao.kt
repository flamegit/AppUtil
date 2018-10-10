package com.example.flame.kotlinstudy.datasource.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.example.flame.kotlinstudy.model.Item

@Dao
interface ItemDao {

    @Query("select * from item")
    fun getAllTasks(): List<Item>

    @Query("select * from item where id = :id")
    fun findTaskById(id: Long): Item

    @Insert(onConflict = REPLACE)
    fun insertTask(task: Item)

    @Update(onConflict = REPLACE)
    fun updateTask(task: Item)

    @Delete
    fun deleteTask(task: Item)
}