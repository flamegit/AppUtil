package com.example.flame.kotlinstudy.model

/**
 * Created by flame on 2018/2/2.
 */
data class HttpBean<out T>(val error:String, val results:T)
