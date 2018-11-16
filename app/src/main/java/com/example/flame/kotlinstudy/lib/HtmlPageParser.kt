package com.example.flame.kotlinstudy.lib

import com.example.flame.kotlinstudy.model.Category
import com.example.flame.kotlinstudy.model.Tag
import org.jsoup.nodes.Document

/**
 * Created by flame on 2018/2/1.
 */

class HtmlPageParser(url: String) : AbstractParser(url) {

    override fun getCategoryList(): List<Category> {
        checkConnect()
        return getCategoryList(document)
    }

    override fun getLadyNum(): Int {
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

    override fun getLadyImage(): String? {
        checkConnect()
        return getLadyImage(document)
    }

    override fun getCategoryList(url: String): List<Category> {
        return getCategoryList(connect(url))
    }

    override fun getLadyImage(url: String): String? {
        return getLadyImage(connect(url))
    }

    private fun getLadyImage(document: Document?): String? {
        var src: String? = null
        document?.let {
            val element = it.select("div.main-image>p>a").first()
            val img = element.getElementsByTag("img").first()
            src = img.attr("src")
        }
        return src
    }

    private fun getCategoryList(document: Document?): List<Category> {
        val list = arrayListOf<Category>()
        document?.let {
            val elements = it.select("ul#pins>li>a")
            for (element in elements) {
                val img = element.getElementsByTag("img").first()
                val lady = Category()
                lady.desc = img.attr("alt")
                lady.cover = img.attr("data-original")
                lady.url = element.attr("href")
                list.add(lady)
            }
        }
        return list
    }

    override fun getTagList(): List<Tag>{
        checkConnect()
        return getTagList(document)
    }

    private fun getTagList(document: Document?): List<Tag> {
        val list = arrayListOf<Tag>()
        document?.let {
            val elements = it.select("dl.tags>dd>a")
            for (element in elements) {
                val img = element.getElementsByTag("img").first()
                val tag = Tag(element.text(), img.attr("src"), url = element.attr("href"))
                list.add(tag)
            }
        }
        return list
    }
}



