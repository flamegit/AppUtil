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

/**
 * Created by flame on 2018/2/18.
 */
class LiveDataGirlViewModel(val api: ApiService): ViewModel(){

    val mData:MutableLiveData<List<Girl>> = MutableLiveData()

    var mLoad=false

    var mPager:Int=1

    private fun load(page:Int){
        api.getGirlList(page).enqueue(object: Callback<HttpGirl> {
            override fun onFailure(call: Call<HttpGirl>?, t: Throwable) {
            }
            override fun onResponse(call: Call<HttpGirl>,response: Response<HttpGirl>) {
                response.body()?.results.let {
                    if(it==null||it.isEmpty()){
                    }else{
                        mData.value=it
                    }
                    mLoad=true
                }
            }
        })
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

