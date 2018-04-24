package com.example.flame.kotlinstudy.lib

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.BR



/**
 * Created by flame on 2017/10/24.
 */
class BaseAdapter<T> : RecyclerView.Adapter<DataBindingViewHolder>() {

    private var mContent: MutableList<T> = mutableListOf()

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.binding?.let {
            it.setVariable(BR.users,mContent[position])
            it.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {

        val binding=DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.activity_login, parent,false)
        val holder= DataBindingViewHolder(binding.root)
        holder.binding=binding
        return holder
    }

    override fun getItemCount()= mContent.size
}