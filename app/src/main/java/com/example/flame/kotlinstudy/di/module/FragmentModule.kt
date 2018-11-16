package com.example.flame.kotlinstudy.di.module

import com.example.flame.kotlinstudy.di.scope.FragmentScope
import com.example.flame.kotlinstudy.lib.AbstractParser
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import com.example.flame.kotlinstudy.lib.HtmlPageParser2
import dagger.Module
import dagger.Provides

/**
 * Created by flame on 2018/2/1.
 */
@Module
class FragmentModule(private val siteType: Int, private val url: String) {

    @FragmentScope
    @Provides
    fun provideParser(): AbstractParser {
        when (siteType) {
            0 -> return HtmlPageParser(url)
            1 -> return HtmlPageParser2(url)
        }
        return HtmlPageParser(url)
    }
}
