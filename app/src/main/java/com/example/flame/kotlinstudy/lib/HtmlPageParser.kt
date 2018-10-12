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


    fun getCategoryList(): List<Category> {
        checkConnect()
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

    fun getLadyNum(): Int {
        checkConnect()
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

    private fun checkConnect() {
        if (document == null) {
            try {
                document = Jsoup.connect(url).get()
            } catch (e: IOException) {
            }
        }
    }


    fun getLadyImage(): String? {
        checkConnect()
        return getLadyImage(document)
    }


    companion object {

        private fun getLadyImage(document: Document?): String? {
            var src: String? = null
            document?.let {
                val element = it.select("div.main-image>p>a").first()
                val img = element.getElementsByTag("img").first()
                src = img.attr("src")
            }
            return src
        }

        fun getLadyImage(url: String): String? {
            var document: Document? = null
            try {
                document = Jsoup.connect(url).get()
            } catch (e: IOException) {
            }
            return getLadyImage(document)
        }
    }
}



