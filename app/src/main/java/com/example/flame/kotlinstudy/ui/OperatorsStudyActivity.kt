package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.CommonAdapter
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_operators_study.*
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class OperatorsStudyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators_study)

        val first = Observable.just("hello").delay(2000, TimeUnit.MILLISECONDS)
        val second = Observable.just("world").delay(1000, TimeUnit.MILLISECONDS)

        val list = arrayListOf("concat", "merge", "zip", "amb", "reduce", "buffer", "flatMap","http")
        var third = first

        val adapter = CommonAdapter<String>(android.R.layout.simple_list_item_1) { holder, position, data ->
            holder.get<TextView>(android.R.id.text1).text = data
            holder.itemView.setOnClickListener {
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
                        Observable.fromIterable(list).buffer(4)
                                .subscribe { s -> content_view.text = "$s" }
                    }
                    6 -> {
//                        RxPermissions(this).request(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                                .subscribe { b ->
//                                    if (b) {
//                                        val list = mutableListOf<String>()
//                                        getAllFile(Environment.getExternalStorageDirectory().absolutePath, list)
//                                        content_view.text = "${list.size}"
//                                    }
//                                }

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

                    7 -> {
                        val client = OkHttpClient.Builder()
                                .build()
                        //构造request对象
                        val request = Request.Builder()
                                .url("https://api.spencerwoo.com")
                                .build()
                        client.newCall(request).enqueue(object : Callback{
                            override fun onResponse(call: Call, response: Response) {
                                content_view.text = response.body().toString()
                            }

                            override fun onFailure(call: Call, e: IOException) {
                            }
                        })
                        return@setOnClickListener
                    }
                }
                third.observeOn(AndroidSchedulers.mainThread())
                        .subscribe { s -> content_view.text = s }

            }
        }
        operator_recycler_view.adapter = adapter
        adapter.addItems(list, false)
    }

    fun getAllFile(name: String, list: MutableList<String>) {
        if(name.endsWith("rmvb")){
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
                Log.d("Operators","${list.size}")
            }
        }
    }

    fun getAllImage() {

    }

}
