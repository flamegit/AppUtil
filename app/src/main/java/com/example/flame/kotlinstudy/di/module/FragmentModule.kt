package com.example.flame.kotlinstudy.di.module

import com.example.flame.kotlinstudy.di.scope.FragmentScope
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import dagger.Module
import dagger.Provides

/**
 * Created by flame on 2018/2/1.
 */
@Module
class FragmentModule(private val url: String){

    @FragmentScope
    @Provides
    fun provideParser():HtmlPageParser{
       return HtmlPageParser(url)
    }

}
