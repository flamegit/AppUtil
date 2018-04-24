package com.example.flame.kotlinstudy.lib

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import com.example.flame.kotlinstudy.BR

/**
 * Created by flame on 2017/10/24.
 */
class DataBindingAdapter<T>(val layoutId:Int) :
        RecyclerView.Adapter<DataBindingViewHolder>() {

    private var mContent: MutableList<T> = mutableListOf()

    override fun onBindViewHolder(holder:DataBindingViewHolder, position: Int) {
        holder.binding?.let {
            it.setVariable(BR.data,mContent[position])
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

    fun addItems(items: Collection<T>?, append: Boolean) {
        items?.let {
            if (!append) {
                mContent.clear()
            }
            mContent.addAll(it)
            notifyDataSetChanged()
        }
    }
}