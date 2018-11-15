package com.example.flame.kotlinstudy.ui

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.TextView
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_operators_study.*
import okhttp3.*
import java.io.File
import java.io.FileOutputStream

import java.util.concurrent.TimeUnit

class OperatorsStudyActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators_study)
        showProgressDialog()

        val first = Observable.just("hello").delay(2000, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
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
                        RxPermissions(this).request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .observeOn(Schedulers.io())
                                .doOnNext{
                                    if(it){
                                        saveFile()
                                    }
                                }.subscribe ()

                    }

                    7 -> {
                        return@setOnClickListener
                    }
                    8 ->{

                    }
                }
                third.observeOn(AndroidSchedulers.mainThread())
                        .subscribe { s -> content_view.text = s }

            }
        }
        operator_recycler_view.adapter = adapter
        adapter.addItems(list, false)
    }

    private fun getSavedFile(albumName: String): File? {

        val file = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName)
        if (!file.mkdirs()) {
            return null
        }
        return File(file,"random.png")
    }

    private fun saveFile(){

        val client = OkHttpClient.Builder().build()
        val request = Request.Builder()
                .url("https://api.spencerwoo.com").build()
        val response=client.newCall(request).execute()
        val inputStream=response.body()?.byteStream()
        getSavedFile("test")?.let {
            val outputStream =FileOutputStream(it)
            inputStream?.copyTo(outputStream)
        }
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

    fun <T> applySchedulers(): ObservableTransformer<T,T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }



    fun showProgressDialog(){
        AlertDialog.Builder(this,R.style.ProgressDialog).setView(R.layout.dialog_progress)
                .create().show()
    }
}
