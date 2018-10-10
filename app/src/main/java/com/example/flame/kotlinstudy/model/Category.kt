package com.example.flame.kotlinstudy.model

/**
 * Created by flame on 2018/2/2.
 */
data class Category(val id: String = "no_id",
                    val desc: String,
                    val cover: String,
                    val url: String,
                    var content: List<Item>? = null)