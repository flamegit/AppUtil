//package com.example.flame.kotlinstudy.lib
//
//
//import org.jsoup.Jsoup
//import org.jsoup.nodes.Document
//import org.jsoup.nodes.Element
//import org.jsoup.select.Elements
//
//import java.io.IOException
//import java.util.ArrayList
//
///**
// * Created by Administrator on 2016/10/14.
// */
//object HtmlParse {
//    // String rule="{\"select\":\"ul#pins>li\",\"num\":\"all\",\"tag\":\"a\",\"attr\":\"href\",\"tag\":\"img\",\"attr\":\"alt\"\"attr\":\"data-original\"}";
//
//    fun getLadyCover(url: String): List<Lady> {
//        val list = ArrayList<Lady>()
//
//        try {
//            val document = Jsoup.connect(url).get()
//            val elements = document.select("ul#pins>li>a")
//            for (element in elements) {
//                val lady = Lady()
//                lady.mUrl = element.attr("href")
//                val img = element.getElementsByTag("img").first()
//                lady.mDes = img.attr("alt")
//                lady.mThumbUrl = img.attr("data-original")
//                list.add(lady)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return list
//    }
//
//    fun getLadyTags(url: String): List<Tag> {
//        val end = url.lastIndexOf("/")
//        val index = Integer.valueOf(url.substring(end + 1))
//        val list = ArrayList<Tag>()
//        var tagIndex = 0
//        try {
//            val document = Jsoup.connect(url.substring(0, end)).get()
//            val dl = document.select("div.postlist>dl.tags").first()
//            for (element in dl.children()) {
//                if (element.tagName() == "dt") {
//                    ++tagIndex
//                    if (tagIndex > index) {
//                        break
//                    } else {
//                        continue
//                    }
//                }
//                if (tagIndex == index) {
//                    val tag = Tag()
//                    val a = element.child(0)
//                    tag.name = a.ownText()
//                    tag.url = a.attr("href")
//                    tag.mThumbUrl = a.child(0).attr("src")
//                    tag.num = element.child(1).text()
//                    list.add(tag)
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return list
//    }
//
//
//    fun getLadyNum(url: String): Int {
//        var num = 0
//        try {
//            val document = Jsoup.connect(url).get()
//            val elements = document.select("div.pagenavi>a")
//            val size = elements.size
//            val a = elements[size - 2]
//            val span = a.getElementsByTag("span").first()
//            num = Integer.valueOf(span.text())
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return num
//    }
//
//    fun getLadyImage(baseUrl: String, index: Int): String? {
//        var src: String? = null
//        try {
//            val url = "$baseUrl/$index"
//            val document = Jsoup.connect(url).get()
//            val element = document.select("div.main-image>p>a").first()
//            val img = element.getElementsByTag("img").first()
//            src = img.attr("src")
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return src
//    }
//
//}
