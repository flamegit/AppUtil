package com.example.flame.kotlinstudy.datasource.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.example.flame.kotlinstudy.model.Item
import io.reactivex.Maybe


@Dao
interface ItemDao {

    @Query("select * from item")
    fun getAllItems(): Maybe<List<Item>>

    @Query("select * from item where id = :id")
    fun findItemById(id: Long): Item

    @Insert(onConflict = REPLACE)
    fun insertItem(task: Item)

    @Update(onConflict = REPLACE)
    fun updateItem(item: Item)

    @Delete
    fun deleteItem(item: Item)
}