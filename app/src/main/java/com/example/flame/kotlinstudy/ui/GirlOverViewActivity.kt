package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.utils.createGlideUrl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_girl_overview.*

class GirlOverViewActivity : AppCompatActivity() {

    lateinit var parser: HtmlPageParser
    var adapter: CommonAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girl_overview)
        val url = intent.getStringExtra(Constants.KEY_URL)
        parser = HtmlPageParser(url)

        adapter = CommonAdapter(R.layout.viewholder_girl) { holder, _, data ->
            Glide.with(this).load(createGlideUrl(data)).into(holder[R.id.image_view])
        }
        recycler_view.adapter = adapter

        loadFirstImage(url)
    }

    private fun loadFirstImage(url: String) {
        val tmp = Observable.fromCallable {
            val imageUrl = parser.getLadyImage()
            val count = parser.getLadyNum()
            return@fromCallable Pair(imageUrl, count)
        }.subscribeOn(Schedulers.io())
                .flatMap {
                    return@flatMap Observable.fromIterable(createUrlList(url, it.second))
                }
                .map { HtmlPageParser.getLadyImage(it) }

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter?.addItem(it)

                }
    }


    private fun loadLeftImage(url: String, count: Int) {
        val dis = Observable.fromIterable(createUrlList(url, count))
                .map {
                    HtmlPageParser.getLadyImage(it)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter?.addItem(it)
                }
    }

    private fun createUrlList(url: String, count: Int): List<String> {
        var i = 2
        val list = mutableListOf<String>()
        while (i <= count) {
            list.add("$url/$i")
            i += 1
        }
        return list
    }
}
