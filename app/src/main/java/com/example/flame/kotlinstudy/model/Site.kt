package com.example.flame.kotlinstudy.model

import com.example.flame.kotlinstudy.lib.AbstractParser
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import com.example.flame.kotlinstudy.lib.HtmlPageParser2

/**
 * Created by flame on 2018/2/2.
 */
class Site(val siteType: Int, val title: String, val nextPagePath: String, val endUrl: String,
           val categoryTitle: Array<String>, val categoryPath: Array<String>,
           val typeTitle: Array<String>, val typePath: Array<String>,val tagPath:String) {
    init {
        currSite = this
    }

    fun getTitle(type: Int): Array<String> {
        when (type) {
            Constants.HOME ->
                return typeTitle
            Constants.CATEGORY ->
                return categoryTitle
            Constants.TAG ->
                return typeTitle
        }
        return typeTitle
    }

    fun getPath(type: Int): Array<String> {
        when (type) {
            Constants.HOME ->
                return typePath
            Constants.CATEGORY ->
                return categoryPath
            Constants.TAG ->
                return typePath
        }
        return typePath
    }

    companion object {
        var currSite: Site? = null
        val titles = arrayOf("妹子图", "美女图","清纯妹子","Cosplay","二次元","自定义")
        val nextPagePath = arrayOf("/page/", "/home/")
        val endUrls = arrayOf("http://www.mzitu.com", "http://www.mmjpg.com")
        val typeTitles = arrayOf(arrayOf("最新", "最热", "推荐"), arrayOf("最新", "排行榜", "推荐"))
        val typePaths = arrayOf(arrayOf("/", "/hot", "/best"), arrayOf("/", "/hot", "/top"))
        val categoryTitles = arrayOf(arrayOf("性感妹子", "日本妹子", "台湾妹子", "清纯妹子"), arrayOf("最新", "最热", "推荐"))
        val categoryPaths = arrayOf(arrayOf("/xinggan","/japan","/taiwan","/mm"), arrayOf("/", "/hot", "/top"))
        val tagPaths = arrayOf("http://www.mzitu.com/zhuanti", "http://www.mmjpg.com/more")

        fun provideSite(siteType: Int): Site {
            return Site(siteType, titles[siteType], nextPagePath[siteType], endUrls[siteType], categoryTitles[siteType], categoryPaths[siteType],
                    typeTitles[siteType], typePaths[siteType],tagPaths[siteType])
        }

        fun provideParser(siteType: Int, url: String?): AbstractParser? {
            if (url != null) {
                when (siteType) {
                    0 -> return HtmlPageParser(url)
                    1 -> return HtmlPageParser2(url)
                }
                return HtmlPageParser(url)
            } else {
                return null
            }
        }

        fun currSiteType():Int{
            return currSite?.siteType?:0
        }
    }
}


