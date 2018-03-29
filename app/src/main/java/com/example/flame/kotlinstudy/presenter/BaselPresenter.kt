package com.example.flame.kotlinstudy.presenter

import com.example.flame.kotlinstudy.view.CommonView

/**
 * Created by flame on 2018/2/2.
 */
 open class BaselPresenter<T> {

    protected var mView:CommonView<T>?=null

    fun attachView(view:CommonView<T>?){

        mView=view
    }


}