package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.bumptech.glide.Glide
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.AbstractParser
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.lib.CommonPagerAdapter
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.model.Site
import com.example.flame.kotlinstudy.utils.createGlideUrl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_girl_overview.*
import uk.co.senab.photoview.PhotoView

class GirlOverViewActivity : AppCompatActivity() {


    private var parser: AbstractParser? = null
    var adapter: CommonAdapter<String>? = null
    var pagerAdapter: CommonPagerAdapter<String>? = null
    var count: Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girl_overview)
        setSupportActionBar(toolbar)
        title = "概览"
        val url = intent.getStringExtra(Constants.KEY_URL)
        val siteType = intent.getIntExtra(Constants.KEY_SITE_TYPE, 0)
        parser = Site.provideParser(siteType, url)
        adapter = CommonAdapter(R.layout.viewholder_girl) { holder, position, data ->
            Glide.with(this).load(createGlideUrl(data)).into(holder[R.id.image_view])
            holder.itemView.setOnClickListener {
                group.visibility = View.VISIBLE
                //recycler_view.visibility = View.INVISIBLE
                girl_view_pager.currentItem = position
            }
        }
        pagerAdapter = CommonPagerAdapter { parent, data ->
            val view = PhotoView(parent.context)
            Glide.with(this).load(createGlideUrl(data)).into(view)
            view
        }
        recycler_view.adapter = adapter
        girl_view_pager.apply {
            this.adapter = pagerAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {}
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
                override fun onPageSelected(p0: Int) {
                    index_view.text = "${p0 + 1}/$count"
                }
            })
        }
        loadFirstImage(url)
    }

    private fun loadFirstImage(url: String) {
        val tmp = Observable.fromCallable {
            val imageUrl = parser?.getLadyImage()
            val count = parser?.getLadyNum()
            return@fromCallable Pair(imageUrl, count)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    count = it.second
                    adapter?.addItem(it.first)
                    pagerAdapter?.addItem(it.first)
                    loadLeftImage(url, it.second)
                }, {

                })
    }

    override fun onBackPressed() {
        if (group.visibility == View.VISIBLE) {
            group.visibility = View.GONE
            //recycler_view.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }

    private fun loadLeftImage(url: String, count: Int?) {
        val dis = Observable.fromIterable(createUrlList(url, count))
                .map {
                    parser?.getLadyImage(it)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter?.addItem(it)
                    pagerAdapter?.addItem(it)
                }
    }

    private fun createUrlList(url: String, count: Int?): List<String> {
        val list = mutableListOf<String>()
        if (count != null) {
            var i = 2
            while (i <= count) {
                list.add("$url/$i")
                i += 1
            }
        }
        return list
    }
}
