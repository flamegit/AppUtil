package com.example.flame.kotlinstudy.ui

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.TextView
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.datasource.net.ApiService
import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.utils.applySchedulers
import com.example.flame.kotlinstudy.utils.toast
import com.jakewharton.rxbinding2.view.RxView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_operators_study.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class OperatorsStudyActivity : RxAppCompatActivity() {

    @Inject
    lateinit var api: ApiService

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as App
        app.component.plus(ActivityModule(this)).inject(this)

        setContentView(R.layout.activity_operators_study)
        val list = arrayListOf("concat", "merge", "amb", "zip", "reduce", "compose", "takeUntil", "retry", "onErrorResumeNext",
                "combineLatest","repeat" )

        val first = Observable.just("hello").delay(2000, TimeUnit.MILLISECONDS)
        val second = Observable.just("world").delay(1000, TimeUnit.MILLISECONDS)

        val subject = PublishSubject.create<String>() //subject

        RxJavaPlugins.setErrorHandler {
            toast(it.toString())
        }
        val cache: MutableList<String> = mutableListOf()

        val maybeCacheObservable = Observable.just(cache).flatMapMaybe {
            if (it.isEmpty()) {
                Maybe.empty()
            } else {
                Maybe.just(cache)
            }
        }

        val singleCacheObservable = Observable.just(cache).flatMapSingle {
            if (it.isEmpty()) {
                Single.error(Exception("emptySetException"))
            } else {
                Single.just(cache)
            }
        }
        // create
        val createObservable = Maybe.create<MutableList<String>> {
            emitter ->
            if(!cache.isEmpty()){
                emitter.onSuccess(cache)
            }
            emitter.onComplete()
        }

        val cacheObservable = Observable.just(cache).delay(500, TimeUnit.MILLISECONDS)

        val netObservable = Observable.just(list).delay(2000, TimeUnit.MILLISECONDS)
                .doOnNext {
                    cache.addAll(it)
                }

        val adapter = CommonAdapter<String>(android.R.layout.simple_list_item_1) { holder, position, data ->
            holder.get<TextView>(android.R.id.text1).text = data
            holder.itemView.setOnClickListener {
                subject.onNext(data)
                content_view.text = ""
                when (position) {
                    0 -> {
                        Observable.concat(cacheObservable, netObservable)
                                .observeOn(AndroidSchedulers.mainThread())
                                .filter { list ->
                                    !list.isEmpty()
                                }
                                .first(mutableListOf())
                                .subscribe { list ->
                                    toast(list.toString())
                                }

                    }
                    1 -> {
                        Observable.merge(maybeCacheObservable, netObservable)
                                .observeOn(AndroidSchedulers.mainThread())
                                .firstElement()
                                .subscribe { list ->
                                    toast(list.toString())
                                }

                    }
                    2 -> {
                        Observable.amb(arrayListOf(cacheObservable, netObservable))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { list ->
                                    toast(list.toString())
                                }
                    }
                    3 -> {
                        Observable.zip(first, second, BiFunction<String, String, String> { s1, s2 -> "$s1 $s2" })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { s ->
                                    toast(s.toString())
                                }
                    }
                    4 -> {
                        Observable.fromIterable(list).reduce { s1, s2 -> "$s1 $s2" }
                                .subscribe { s -> content_view.text = s }
                    }
                    5 -> {
                        api.getImage("https://api.spencerwoo.com")
                                .compose(this.bindToLifecycle())
                                .compose(applySchedulers())
                                .retryWhen { errorObservable ->
                                    errorObservable.flatMap { throwable ->
                                        if (throwable is TimeoutException) {
                                            Observable.just("retry")
                                        } else {
                                            errorObservable
                                        }
                                    }
                                }
                                .map {
                                    BitmapFactory.decodeStream(it.byteStream())
                                }.subscribe {
                                    result_image_view.setImageBitmap(it)
                                }


                    }
                    6 -> {
                        RxView.clicks(textView)
                                .takeUntil(Observable.intervalRange(0, 5, 0, 1,
                                        TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                                        .doOnNext { s -> content_view.text = "$s" }
                                        .filter { num -> num >= 4 })
                                .subscribe({
                                    toast("jump")
                                }, {}, {
                                    toast("jump")
                                })
                    }

                    7 -> {
                        RxView.clicks(textView).flatMap {
                            Observable.error<String>(Throwable("error"))
                        }.retry()
                                .subscribe({}, { throwable ->
                                    toast(throwable.message ?: "")
                                })
                    }
                    8 -> {
                        Observable.concat(singleCacheObservable, netObservable)
                                .onErrorResumeNext(netObservable)
                                .observeOn(AndroidSchedulers.mainThread())
                                .firstElement()
                                .subscribe { list ->
                                    toast(list.toString())
                                }
                    }
                    9 -> {
                        Observable.combineLatest<Int,String,String>(Observable.fromArray(1,2,3),Observable.fromIterable(list)
                                ,BiFunction<Int, String, String> { s1, s2 -> "$s1 $s2" }).subscribe {
                            c -> content_view.append("$c")
                        }
                    }

                    10 -> {

                        first .observeOn(AndroidSchedulers.mainThread()).repeat(3).subscribe{
                            c -> content_view.append("$c")
                        }
//                        RxPermissions(this).request(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                        Observable.just("/storage/emulated/0")
//                                .subscribeOn(Schedulers.io())
//                                .flatMap { file ->
//                                    val list = mutableListOf<String>()
//                                    getAllFile(file, list)
//                                    Observable.fromIterable(list)
//                                }.count()
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe { c -> content_view.text = "$c" }
                    }

                }

            }
        }

        operator_recycler_view.adapter = adapter
        adapter.addItems(list, false)

        subject.buffer(4)
                .subscribe {
                    toast(it.toString())
                }

    }

    fun showProgressDialog() {
        AlertDialog.Builder(this, R.style.ProgressDialog).setView(R.layout.dialog_progress)
                .create().show()
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    /****************************************************************************/
    private fun getSavedFile(albumName: String): File? {
        val file = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName)
        if (!file.mkdirs()) {
            return null
        }
        return File(file, "random.png")
    }

    private fun saveFile() {

        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
                .url("https://api.spencerwoo.com").build()
        val response = client.newCall(request).execute()
        val inputStream = response.body()?.byteStream()
        getSavedFile("test")?.let {
            val outputStream = FileOutputStream(it)
            inputStream?.copyTo(outputStream)
        }
    }

    fun getAllFile(name: String, list: MutableList<String>) {
        if (name.endsWith("rmvb")) {
            return
        }
        val file = File(name)
        if (file.isDirectory and file.canRead()) {
            if (file.listFiles() == null) {
                return
            }
            file.listFiles().forEach { getAllFile(it.absolutePath, list) }
        } else {
            if (file.name.endsWith(".png")) {
                list.add(file.name)
                Log.d("Operators", "${list.size}")
            }
        }
    }

    override fun onDestroy() {

        super.onDestroy()

    }
}
