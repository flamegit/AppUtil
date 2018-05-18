package com.example.flame.kotlinstudy.lib

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.flame.kotlinstudy.R

class DefaultDelegateAdapter :ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.viewholder_girl,parent,false)
        return CommonViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int,data :Any?) {

        if(data is String){
            holder.get<TextView>(android.R.id.text1).text=data
            holder.get<TextView>(android.R.id.text2).text=data
        }
    }
}