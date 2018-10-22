package com.example.flame.kotlinstudy.lib

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.model.Girl
import com.example.flame.kotlinstudy.ui.BehaviorActivity
import com.example.flame.kotlinstudy.utils.openActivity

class GirlDelegateAdapter :ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view= LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_girl,parent,false)
        return CommonViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int,data :Any?) {
        if(data is Girl){
            val context=holder.itemView.context
            holder.get<TextView>(R.id.des_view).text=data.desc
            Glide.with(context).load(data.url).into(holder.get(R.id.image_view))
            holder.itemView.setOnClickListener{ context.openActivity(BehaviorActivity::class.java)}
        }
    }
}