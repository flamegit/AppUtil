package com.example.flame.kotlinstudy.di.module

import com.example.flame.kotlinstudy.di.scope.ActivityScope
import com.example.flame.kotlinstudy.model.Site
import dagger.Module
import dagger.Provides

/**
 * Created by flame on 2018/2/1.
 */
@Module
class ContentActivityModule(private val site:Int){
    val titles= arrayOf("妹子图","美女图")
    val endUrls= arrayOf("","http://www.mmjpg.com/")
    val categoryTitles= arrayOf(arrayOf("最新", "最热", "推荐"),arrayOf("最新", "最热", "推荐"))
    val categoryPaths= arrayOf(arrayOf("/", "/hot", "/best"),arrayOf("/", "/hot", "/best"))
    val typeTitles= arrayOf(arrayOf("最新", "最热", "推荐"),arrayOf("最新", "最热", "推荐"))
    val typePaths= arrayOf(arrayOf("/", "/hot", "/best"),arrayOf("/", "/hot", "/best"))
    val tagTitles= arrayOf("","http://www.mmjpg.com/")
    val tagPaths =arrayOf("","http://www.mmjpg.com/")

    @ActivityScope
    @Provides
    fun provideSite(): Site {
       return Site(site,titles[site],endUrls[site],categoryTitles[site],categoryPaths[site],
               typeTitles[site],typePaths[site])
    }

}
