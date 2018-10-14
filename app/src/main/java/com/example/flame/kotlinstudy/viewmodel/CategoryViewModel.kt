package com.example.flame.kotlinstudy.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.flame.kotlinstudy.datasource.database.CategoryDao
import com.example.flame.kotlinstudy.lib.AbstractParser
import com.example.flame.kotlinstudy.lib.HtmlPageParser
import com.example.flame.kotlinstudy.model.Category
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by flame on 2018/2/18.
 */
class CategoryViewModel(val parser: AbstractParser, val categoryDao: CategoryDao) : ViewModel() {

    val content: MutableLiveData<List<Category>> = MutableLiveData()
    var loading = false
    private var disposable: Disposable? = null
    @Volatile
    var page: Int = 1

    private fun load(page: Int) {
        loading = true
        disposable = Observable.fromCallable {
            if (page == 1) {
                parser.getCategoryList()
            } else {
                parser.getCategoryList("${parser.url}/page/$page")
            }

        }.doOnNext {categories->
            val list = categoryDao.getAllCategory()
            categories.forEach {
                if(list.contains(it)){
                    it.isFavorite=true
                }
            }
        }.subscribeOn(Schedulers.io())
                .subscribe(
                        { it ->
                            content.postValue(it)
                            loading = false
                        },
                        { _ -> loading = false }
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

