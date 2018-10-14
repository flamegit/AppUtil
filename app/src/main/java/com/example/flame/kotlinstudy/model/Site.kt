package com.example.flame.kotlinstudy.model

import com.example.flame.kotlinstudy.lib.AbstractParser
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import com.example.flame.kotlinstudy.lib.HtmlPageParser2

/**
 * Created by flame on 2018/2/2.
 */
class Site(val siteType: Int, val title: String, val endUrl: String, val categoryTitle: Array<String>,
           val categoryPath: Array<String>, val typeTitle: Array<String>,
           val typePath: Array<String>) {
    init {
        currType = siteType
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
                return categoryTitle
            Constants.TAG ->
                return typePath
        }
        return typePath
    }

    companion object {
        var currType = 0
        val titles = arrayOf("妹子图", "美女图")
        val endUrls = arrayOf("http://www.mzitu.com", "http://www.mmjpg.com")
        val categoryTitles = arrayOf(arrayOf("最新", "最热", "推荐"), arrayOf("最新", "排行榜", "推荐"))
        val categoryPaths = arrayOf(arrayOf("/", "/hot", "/best"), arrayOf("/", "/hot", "/top"))
        val typeTitles = arrayOf(arrayOf("最新", "最热", "推荐"), arrayOf("最新", "最热", "推荐"))
        val typePaths = arrayOf(arrayOf("/", "/hot", "/best"), arrayOf("/", "/hot", "/best"))
        val tagTitles = arrayOf("", "http://www.mmjpg.com/")
        val tagPaths = arrayOf("", "http://www.mmjpg.com/")

        fun provideSite(siteType: Int): Site {
            return Site(siteType, titles[siteType], endUrls[siteType], categoryTitles[siteType], categoryPaths[siteType],
                    typeTitles[siteType], typePaths[siteType])
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
    }
}


