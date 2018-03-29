package com.example.flame.kotlinstudy.view

/**
 * Created by flame on 2018/2/2.
 */
interface CommonView<in T> {

    fun success(data:T?)
    fun failed(code:Int)
}