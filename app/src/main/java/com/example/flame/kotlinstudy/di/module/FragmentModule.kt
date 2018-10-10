package com.example.flame.kotlinstudy.di.module

import android.app.Fragment
import com.example.flame.kotlinstudy.di.scope.FragmentScope
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import dagger.Module
import dagger.Provides

/**
 * Created by flame on 2018/2/1.
 */
@Module
class FragmentModule(private val fragment: Fragment){

    @FragmentScope
    @Provides
    fun provideParser():HtmlPageParser{
       return HtmlPageParser("")
    }

}
