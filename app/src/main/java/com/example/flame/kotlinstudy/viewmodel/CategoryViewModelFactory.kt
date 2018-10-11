package com.example.flame.kotlinstudy.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import javax.inject.Inject

/**
 * Created by flame on 2018/2/19.
 */

class CategoryViewModelFactory @Inject constructor(val parser: HtmlPageParser):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(parser) as T
        }else if(modelClass.isAssignableFrom(CategoryViewModel::class.java)){
            //return CategroyViewModel("dd")
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}