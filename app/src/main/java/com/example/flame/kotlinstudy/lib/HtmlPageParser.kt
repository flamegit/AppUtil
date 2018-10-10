package com.example.flame.kotlinstudy.lib

import com.example.flame.kotlinstudy.model.Category
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


/**
 * Created by flame on 2018/2/1.
 */

class HtmlPageParser(val url: String) {

    private var document: Document? = null

    init {
        try {
            document = Jsoup.connect(url).get()
        } catch (e: IOException) { }
    }

    fun getCategoryList(): List<Category> {
        val list = arrayListOf<Category>()
        document?.let {
            val elements = it.select("ul#pins>li>a")
            for (element in elements) {
                val img = element.getElementsByTag("img").first()
                val lady = Category(desc = img.attr("alt"), cover = img.attr("data-original"),
                        url = element.attr("href"))
                list.add(lady)
            }
        }
        return list
    }

    fun getLadyNum(url: String): Int {
        var num = 0
        document?.let {
            val elements = it.select("div.pagenavi>a")
            val size = elements.size
            if (size >= 2) {
                val a = elements[size - 2]
                val span = a.getElementsByTag("span").first()
                num = Integer.valueOf(span.text())
            }
        }
        return num
    }

}



