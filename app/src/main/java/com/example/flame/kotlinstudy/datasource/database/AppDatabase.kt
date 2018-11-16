package com.example.flame.kotlinstudy.datasource.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.flame.kotlinstudy.model.Category
import com.example.flame.kotlinstudy.model.Item

@Database(entities = [Item::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    abstract fun categoryDao(): CategoryDao
}