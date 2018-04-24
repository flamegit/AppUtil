package com.example.flame.kotlinstudy.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.flame.kotlinstudy.lib.DataBindingAdapter
import com.example.flame.kotlinstudy.model.Girl


/**
 * Created by flame on 2018/2/17.
 */

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}

@BindingAdapter("app:items")
fun addItem(view: RecyclerView, oldItems: List<Girl>?,newItems: List<Girl>?) {
    val adapter= view.adapter as DataBindingAdapter<Girl>

    var append=true
    if(oldItems==null ||oldItems.isEmpty()){
        append=false
    }else{
        view.context.toast("append")
    }
    adapter.addItems(newItems,false)
}



