package com.example.flame.kotlinstudy.lib

import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import com.example.flame.kotlinstudy.BR
import com.example.flame.kotlinstudy.DataBindingViewHolder

/**
 * Created by flame on 2017/10/24.
 */
class DataBindingAdapter<T>(val layoutId:Int, val mContent:List<T>) :
        RecyclerView.Adapter<DataBindingViewHolder>() {

    override fun onBindViewHolder(holder:DataBindingViewHolder, position: Int) {
        holder.binding?.let {
            it.setVariable(BR.users,mContent[position])
            it.executePendingBindings() //立刻执行数据绑定
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding=DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), layoutId, parent,false)
        val holder= DataBindingViewHolder(binding.root)
        holder.binding=binding
        return holder
    }
    override fun getItemCount()= mContent.size
}