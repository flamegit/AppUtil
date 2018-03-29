package com.example.flame.kotlinstudy.presenter

import com.example.flame.kotlinstudy.datasource.net.ApiService
import com.example.flame.kotlinstudy.model.Girl
import com.example.flame.kotlinstudy.model.HttpBean
import com.example.flame.kotlinstudy.model.HttpGirl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by flame on 2018/2/2.
 */

class GirlPresenter @Inject constructor( val api:ApiService) :BaselPresenter<List<Girl>>(){

    private var mPager:Int=1

    private fun load(page:Int){
       api.getGirlList(page).enqueue(object :Callback<HttpGirl>{
           override fun onFailure(call: Call<HttpGirl>?, t: Throwable?) {
               mView?.failed(400)
           }

           override fun onResponse(call: Call<HttpGirl>?, response: Response<HttpGirl>?) {
               mView?.success(response?.body()?.results)
           }
       })
    }

    fun load(){
        mPager=1
        load(1)
    }

    fun loadMore(){
        load(++mPager)
    }

}