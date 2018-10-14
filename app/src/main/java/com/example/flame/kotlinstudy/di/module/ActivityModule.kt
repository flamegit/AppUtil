package com.example.flame.kotlinstudy.di.module

import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.di.scope.ActivityScope
import com.example.flame.kotlinstudy.di.scope.FragmentScope
import com.example.flame.kotlinstudy.lib.AbstractParser
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import com.example.flame.kotlinstudy.lib.HtmlPageParser2
import com.example.flame.kotlinstudy.model.Girl
import com.example.flame.kotlinstudy.model.Site
import com.example.flame.kotlinstudy.utils.openActivity
import com.example.flame.kotlinstudy.ui.BehaviorActivity
import dagger.Module
import dagger.Provides

/**
 * Created by flame on 2018/2/1.
 */
@Module
class ActivityModule(private val activity:AppCompatActivity,private val siteType:Int,private val url:String?){

    constructor( activity:AppCompatActivity):this(activity,0,null)
    constructor( activity:AppCompatActivity,siteType:Int):this(activity,siteType,null)

    @ActivityScope
    @Provides
    fun provideHomeAdapter():CommonAdapter<Girl>{
        return CommonAdapter(R.layout.viewholder_girl) { h, _, d ->
            h.get<TextView>(R.id.des_view).text=d.desc
            Glide.with(activity).load(d.url).fitCenter().into(h.get(R.id.image_view))
            h.itemView.setOnClickListener({ activity.openActivity(BehaviorActivity::class.java)})
        }
    }

    val titles= arrayOf("妹子图","美女图")
    val endUrls= arrayOf("","http://www.mmjpg.com/")
    val categoryTitles= arrayOf(arrayOf("最新", "最热", "推荐"),arrayOf("最新", "最热", "推荐"))
    val categoryPaths= arrayOf(arrayOf("/", "/hot", "/best"),arrayOf("/", "/hot", "/top"))
    val typeTitles= arrayOf(arrayOf("性感妹子", "日本妹子", "台湾妹子", "清纯妹子"),arrayOf("最新", "最热", "推荐"))
    val typePaths= arrayOf(arrayOf("/xinggan","/japan","/taiwan","/mm"),arrayOf("/", "/hot", "/top"))
    val tagTitles= arrayOf("","http://www.mmjpg.com/")
    val tagPaths =arrayOf("","http://www.mmjpg.com/")

    @ActivityScope
    @Provides
    fun provideSite(): Site {
        return Site(siteType,titles[siteType],endUrls[siteType],categoryTitles[siteType],categoryPaths[siteType],
                typeTitles[siteType],typePaths[siteType])
    }

    @ActivityScope
    @Provides
    fun provideParser(): AbstractParser? {
       return Site.provideParser(siteType,url)
    }
}
