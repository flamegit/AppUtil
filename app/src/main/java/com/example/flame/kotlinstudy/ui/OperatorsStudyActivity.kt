package com.example.flame.kotlinstudy.ui

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.TextView
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.datasource.net.ApiService
import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.utils.applySchedulers
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.example.flame.kotlinstudy.utils.toast
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_operators_study.*
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
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
        val list = arrayListOf("concat", "merge", "zip", "amb", "reduce", "buffer", "flatMap", "http", "error","takeuntil")
        val first = Observable.just("hello").delay(2000, TimeUnit.MILLISECONDS)
        val second = Observable.just("world").delay(1000, TimeUnit.MILLISECONDS)
        val subject = PublishSubject.create<String>()

        val bitmapObservable = RxView.clicks(textView).throttleFirst(5, TimeUnit.SECONDS)
                .observeOn(Schedulers.io()).flatMap {
                    api.getImage("https://api.spencerwoo.com")
                }.map {
                    BitmapFactory.decodeStream(it.byteStream())
                }
        bitmapObservable
                .subscribe(object : DisposableObserver<Bitmap>() {
                    override fun onNext(t: Bitmap) {
                        result_image_view.setImageBitmap(t)
                    }
                    override fun onComplete() {
                        this@OperatorsStudyActivity.toast("onComplete")
                    }

                    override fun onError(e: Throwable) {
                        this@OperatorsStudyActivity.toast(e.toString())
                    }

                    override fun onStart() {
                        super.onStart()
                        this@OperatorsStudyActivity.toast("onStart")
                    }
                }
                )

        var third = first

        val adapter = CommonAdapter<String>(android.R.layout.simple_list_item_1) { holder, position, data ->
            holder.get<TextView>(android.R.id.text1).text = data
            holder.itemView.setOnClickListener {
                subject.onNext(data)
                content_view.text = ""
                when (position) {
                    0 -> {
                        third = Observable.concat(first, second)
                    }
                    1 -> {
                        third = Observable.merge(first, second)
                    }
                    2 -> {
                        third = Observable.zip(first, second, BiFunction { s1, s2 -> "$s1$s2" })
                    }
                    3 -> {
                        third = Observable.amb(arrayListOf(first, second))
                    }
                    4 -> {
                        Observable.fromIterable(list).reduce { s1, s2 -> "$s1 $s2" }
                                .subscribe { s -> content_view.text = s }
                        return@setOnClickListener
                    }
                    5 -> {
                        RxView.clicks(textView).flatMap { _ ->
                            Observable.error<String>(Throwable("error"))
                                    .retry()
                        }.subscribe ({
                            toast(it)
                        },{
                            toast(it.message?:"")
                        })
                        return@setOnClickListener
                    }
                    6 -> {
                        RxPermissions(this).request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .observeOn(Schedulers.io())
                                .doOnNext {
                                    if (it) {
                                        saveFile()
                                    }
                                }.subscribe()
                    }

                    7 -> {
                        api.getImage("https://api.spencerwoo.com")
                                .compose(this.bindToLifecycle())
                                .compose(applySchedulers())
                                .map {
                                    BitmapFactory.decodeStream(it.byteStream())
                                }.subscribe{
                                    result_image_view.setImageBitmap(it)
                                }
                        return@setOnClickListener
                    }
                    8 -> {
                        RxPermissions(this).request(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        Observable.just("/storage/emulated/0")
                                .subscribeOn(Schedulers.io())
                                .flatMap { file ->
                                    val list = mutableListOf<String>()
                                    getAllFile(file, list)
                                    Observable.fromIterable(list)
                                }.count()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { c -> content_view.text = "$c" }
                    }
                    9 -> {
                        RxView.clicks(textView).takeUntil(Observable.timer(3,TimeUnit.SECONDS,AndroidSchedulers.mainThread()))
                                .subscribe({
                                    toast("jump")
                                },{},{
                                    toast("jump")
                                })
                    }

                }
                third.observeOn(AndroidSchedulers.mainThread())
                        .subscribe { s -> content_view.text = s }
            }
        }

        operator_recycler_view.adapter = adapter
        adapter.addItems(list, false)
        subject.buffer(4)
                .subscribe {
                    toast(it.toString())
                }
    }

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

    fun showProgressDialog() {
        AlertDialog.Builder(this, R.style.ProgressDialog).setView(R.layout.dialog_progress)
                .create().show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
