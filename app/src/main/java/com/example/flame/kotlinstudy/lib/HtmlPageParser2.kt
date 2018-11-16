package com.example.flame.kotlinstudy.lib

import com.example.flame.kotlinstudy.model.Category
import com.example.flame.kotlinstudy.model.Tag
import org.jsoup.nodes.Document

/**
 * Created by flame on 2018/2/1.
 */

class HtmlPageParser2(url: String) : AbstractParser(url){

    override fun getCategoryList(): List<Category> {
        checkConnect()
        return getCategoryList(document)
    }
    override fun getLadyNum(): Int {
        checkConnect()
        var num = 0
        document?.let {
            val elements = it.select("div#page>a")
            val size = elements.size
            if (size >= 2) {
                val a = elements[size - 2]
                num = Integer.valueOf(a.text())
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
            val img = it.select("div#content>a>img").first()
            src = img.attr("src")
        }
        return src
    }

    override fun getTagList(): List<Tag> {
        checkConnect()
        return getTagList(document)
    }

    private fun getTagList(document: Document?):List<Tag>{
        val list = arrayListOf<Tag>()
        document?.let {
            val elements = it.select("ul#morelist>li>a")
            for (element in elements) {
                val img = element.getElementsByTag("img").first()
                val tag = Tag(element.text(), img.attr("data-img"), url = element.attr("href"))
                list.add(tag)
            }
        }
        return list
    }

    private fun getCategoryList(document: Document?): List<Category> {
        val list = arrayListOf<Category>()
        document?.let {
            val elements = it.select("div.pic>ul>li")
            for (element in elements) {
                val a = element.getElementsByTag("a").first()
                val img = a.getElementsByTag("img")
                val lady = Category()
                lady.desc = img.attr("alt")
                lady.cover = img.attr("src")
                lady.url = a.attr("href")
                list.add(lady)
            }
        }
        return list
    }
}



