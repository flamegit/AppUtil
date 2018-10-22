package com.example.flame.kotlinstudy.ui

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.*
import android.support.v4.content.FileProvider
import android.support.v4.view.ViewPager
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.BuildConfig
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.datasource.database.ItemDao
import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.lib.AbstractParser
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.lib.CommonPagerAdapter
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.model.Item
import com.example.flame.kotlinstudy.model.Site
import com.example.flame.kotlinstudy.utils.createGlideUrl
import com.example.flame.kotlinstudy.utils.toast
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_girl_overview.*
import okhttp3.OkHttpClient
import okhttp3.Request
import uk.co.senab.photoview.PhotoView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class GirlOverViewActivity : AppCompatActivity() {

    @Inject
    @JvmField
    var parser: AbstractParser? = null
    @Inject
    lateinit var itemDao: ItemDao
    var adapter: CommonAdapter<String>? = null
    private var pagerAdapter: CommonPagerAdapter<String>? = null
    var count: Int? = 1
    var index = 0
    var desc: String? = null
    private val compositeDisposable = CompositeDisposable()
    private var favorites: List<String>? = null
    //private var progressDrawable:CircularProgressDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(Constants.KEY_URL)
        val app = application as App
        app.component.plus(ActivityModule(this, url)).inject(this)
        setContentView(R.layout.activity_girl_overview)
        val isFavorite = intent.getBooleanExtra(Constants.KEY_FAVORITE, false)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val listener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                index = p0
                index_view.text = "${p0 + 1}/$count"
                val currUrl = pagerAdapter?.getContent(p0)
                if (isFavorite(currUrl)) {
                    favorite_view.setImageResource(R.drawable.ic_favorite_red)
                }else{
                    favorite_view.setImageResource(R.drawable.ic_favorite_border_white)
                }
            }
        }
        adapter = CommonAdapter(R.layout.viewholder_girl_overview) { holder, position, data ->
            val options = RequestOptions().placeholder(CircularProgressDrawable(this))
            Glide.with(this).load(createGlideUrl(data)).apply(options).into(holder[R.id.image_view])
            holder.itemView.setOnClickListener {
                group.visibility = View.VISIBLE
                girl_view_pager.currentItem = position
                if(position==0){
                    listener.onPageSelected(0)
                }
            }
        }
        pagerAdapter = CommonPagerAdapter { parent, data ->
            val view = PhotoView(parent.context)
            Glide.with(this).load(createGlideUrl(data)).into(view)
            view
        }
        recycler_view.adapter = adapter
        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect?.set(5, 5, 5, 5)
            }
        })
        girl_view_pager.apply {
            this.adapter = pagerAdapter
            addOnPageChangeListener(listener)
        }
        if (isFavorite) {
            loadFavorite(true)
            title = "Favorite"
        } else {
            loadFavorite(false)
            desc = intent.getStringExtra(Constants.KEY_DESC)
            title = desc
            loadFirstImage(url)
        }

        share_view.setOnClickListener {
            share(true)
        }
        favorite_view.setOnClickListener {
            favorite_view.isEnabled =false
            val currUrl = pagerAdapter?.getContent(index)
            if(isFavorite(currUrl)){
                return@setOnClickListener
            }
            favorite_view.setImageResource(R.drawable.ic_favorite_red)
            Completable.fromAction {
                val item = Item()
                item.desc = desc
                item.url =currUrl
                itemDao.insertItem(item)
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        this@GirlOverViewActivity.toast("success")
                    }
        }
    }

    private fun loadFavorite(show: Boolean) {
        val disposable = itemDao.getAllItems().subscribe({
            val list = transform(it)
            favorites = list
            if (show) {
                adapter?.addItems(list, false)
                pagerAdapter?.addItems(list)
            }
        }, {
            it.printStackTrace()
        })
        compositeDisposable.add(disposable)
    }

    private fun transform(items: List<Item>): List<String> {
        val list = mutableListOf<String>()
        items.forEach { item ->
            item.url?.let {
                list.add(it)
            }
        }
        return list
    }

    private fun loadFirstImage(url: String) {
        val disposable = Observable.fromCallable {
            val fistItem = parser?.getLadyImage()
            count = parser?.getLadyNum()
            return@fromCallable Pair(fistItem, count)
        }.doOnNext {
            Handler(Looper.getMainLooper()).post {
                adapter?.addItem(it.first)
                pagerAdapter?.addItem(it.first)
            }
        }.flatMap {
            Observable.fromIterable(createUrlList(url, it.second))
        }.map {
            parser?.getLadyImage(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter?.addItem(it)
                    pagerAdapter?.addItem(it)

                }, {
                    it.printStackTrace()
                })
        compositeDisposable.add(disposable)
    }

    override fun onBackPressed() {
        if (group.visibility == View.VISIBLE) {
            group.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    private fun isFavorite(url:String?):Boolean {
        favorites?.let {
            if (it.contains(url)) {
                return true
            }
        }
        return false
    }

    private fun getSaveFile(): File? {
        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "GirlDir")
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null
            }
        }
        return File(dir, "$desc$index")
    }

    private fun saveImage(): Boolean {
        val saveFile = getSaveFile()
        saveFile ?: return false
        val outputStream = FileOutputStream(saveFile)
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
                .url(pagerAdapter?.getContent(index) ?: "")
                .addHeader("Referer", Site.currSite?.endUrl ?: "")
                .build()
        val call = okHttpClient.newCall(request)
        var size: Long = 0
        try {
            val response = call.execute()
            size = response.body()?.byteStream()?.copyTo(outputStream) ?: 0
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return size > 0
    }

    private fun share(save: Boolean) {
        val saveFile = getSaveFile()
        saveFile ?: return
        if (saveFile.exists()) {
            val intent = Intent()
            intent.putExtra(Intent.EXTRA_TITLE, desc)
            intent.action = Intent.ACTION_SEND
            intent.type = "image/jpeg"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            var uri = Uri.fromFile(saveFile)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", saveFile)
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(intent, "share"))
        } else if (save) {
            Observable.fromCallable {
                saveImage()
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it) {
                            share(false)
                        }
                    }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
