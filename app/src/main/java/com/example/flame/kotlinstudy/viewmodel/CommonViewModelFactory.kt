package com.example.flame.kotlinstudy.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.flame.kotlinstudy.datasource.net.ApiService
import javax.inject.Inject

/**
 * Created by flame on 2018/2/19.
 */

class CommonViewModelFactory @Inject constructor(val api: ApiService):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LiveDataGirlViewModel::class.java)) {
            return LiveDataGirlViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}