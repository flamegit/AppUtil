package com.example.flame.kotlinstudy.lib

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.flame.kotlinstudy.R

class BaseDelegateAdapter(private val layoutId:Int) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return CommonViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int,data :Any?) {

    }
}