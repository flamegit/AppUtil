package com.example.flame.kotlinstudy.datasource.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.flame.kotlinstudy.model.Item

@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): ItemDao
}