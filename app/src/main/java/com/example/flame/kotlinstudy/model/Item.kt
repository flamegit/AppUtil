package com.example.flame.kotlinstudy.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by flame on 2018/2/2.
 */
@Entity(tableName = "item")
data class Item(@ColumnInfo(name = "desc") var desc: String, @ColumnInfo(name = "url") var url: String) {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}