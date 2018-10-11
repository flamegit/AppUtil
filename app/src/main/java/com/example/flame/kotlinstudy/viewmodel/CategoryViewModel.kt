package com.example.flame.kotlinstudy.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.flame.kotlinstudy.datasource.net.ApiService
import com.example.flame.kotlinstudy.model.Girl
import com.example.flame.kotlinstudy.model.HttpGirl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.arch.lifecycle.ViewModel
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import com.example.flame.kotlinstudy.model.Category
import io.reactivex.Observable

/**
 * Created by flame on 2018/2/18.
 */
class CategoryViewModel(val parser: HtmlPageParser): ViewModel(){

    val content:MutableLiveData<List<Category>> = MutableLiveData()
    var mLoad=false
    var mPager:Int=1

    private fun load(page:Int){
        Observable.fromCallable {
            parser.getCategoryList()
        }.subscribe{
            it->content.postValue(it)
        }
    }

    fun load(){
        if(mLoad) return
        mPager=1
        load(1)
    }

    fun loadMore(){
        load(++mPager)
    }
}

