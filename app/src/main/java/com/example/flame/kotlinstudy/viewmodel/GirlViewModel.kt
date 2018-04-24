package com.example.flame.kotlinstudy.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableInt
import com.example.flame.kotlinstudy.datasource.net.ApiService
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.model.Girl
import com.example.flame.kotlinstudy.model.HttpGirl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by flame on 2018/2/18.
 */
class GirlViewModel @Inject constructor(val api: ApiService){

    val items=ObservableArrayList<Girl>()
    var mState=ObservableInt(Constants.EMPTY)

    private var mPager:Int=1

    private fun load(page:Int){
        mState.set(Constants.LOADING)
        api.getGirlList(page).enqueue(object: Callback<HttpGirl> {
            override fun onFailure(call: Call<HttpGirl>?, t: Throwable) {
                mState.set(Constants.ERROR)
            }
            override fun onResponse(call: Call<HttpGirl>,response: Response<HttpGirl>) {
                response.body()?.results.let {
                    if(it==null||it.isEmpty()){
                        mState.set(Constants.EMPTY)
                    }else{
                        items.addAll(it)
                        mState.set(Constants.COMPLETE)
                    }
                }
            }
        })
    }
    //fun getDate()=mData.

    fun isLoading() = mState.get()==Constants.LOADING

    fun load(){
        mPager=1
        load(1)
    }

    fun loadMore(){
        if(mState.get()!=Constants.LOADING){
            load(++mPager)
        }
    }
}

