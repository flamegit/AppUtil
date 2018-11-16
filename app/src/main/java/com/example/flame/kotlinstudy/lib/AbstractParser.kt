package com.example.flame.kotlinstudy.lib

import com.example.flame.kotlinstudy.model.Category
import com.example.flame.kotlinstudy.model.Tag
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

/**
 * Created by flame on 2018/2/1.
 */

abstract class AbstractParser(val url: String) {

    protected var document: Document? = null
    abstract fun getCategoryList(): List<Category>
    abstract fun getLadyNum(): Int
    abstract fun getLadyImage(): String?
    abstract fun getCategoryList(url: String): List<Category>
    abstract fun getLadyImage(url: String): String?
    abstract fun getTagList():List<Tag>

    protected fun checkConnect() {
        if (document == null) {
            try {
                document = Jsoup.connect(url).get()
            } catch (e: IOException) {
            }
        }
    }
    protected fun connect(url: String): Document? {
        var document: Document? = null
        try {
            document = Jsoup.connect(url).get()
        } catch (e: IOException) {
        }
        return document
    }
}



