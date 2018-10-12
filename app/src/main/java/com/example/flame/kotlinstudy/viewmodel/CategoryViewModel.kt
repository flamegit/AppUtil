package com.example.flame.kotlinstudy.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import com.example.flame.kotlinstudy.model.Category
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by flame on 2018/2/18.
 */
class CategoryViewModel(val parser: HtmlPageParser) : ViewModel() {

    val content: MutableLiveData<List<Category>> = MutableLiveData()
    var loading = false
    private var disposable: Disposable? = null
    @Volatile var page: Int = 1

    private fun load(page: Int) {
        loading = true
        disposable = Observable.fromCallable {
            if (page == 1) {
                parser.getCategoryList()
            } else {
                HtmlPageParser.getCategoryList("${parser.url}/$page")
            }

        }.subscribeOn(Schedulers.io())
                .subscribe(
                        { it ->
                            content.postValue(it)
                            loading = false
                        },
                        { _ -> loading =false}
                )
    }

    fun load() {
        if (loading) return
        page = 1
        load(1)
    }

    fun loadMore() {
        load(++page)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

