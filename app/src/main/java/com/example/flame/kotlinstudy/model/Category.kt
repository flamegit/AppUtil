package com.example.flame.kotlinstudy.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Created by flame on 2018/2/2.
 */
@Entity(tableName = "category")
class Category {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @ColumnInfo(name = "desc")
    var desc: String? = null
    @ColumnInfo(name = "cover")
    var cover: String? = null
    @ColumnInfo(name = "url")
    var url: String? = null
    @Ignore
    var isFavorite: Boolean = false

    override fun equals(other: Any?): Boolean {
        other.let {
            if (it is Category) {
                return it.url == url
            }
        }
        return false
    }
}