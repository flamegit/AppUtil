package com.example.flame.kotlinstudy.di.module

import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.di.scope.ActivityScope
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.model.Girl
import com.example.flame.kotlinstudy.utils.openActivity
import com.example.flame.kotlinstudy.ui.BehaviorActivity
import dagger.Module
import dagger.Provides

/**
 * Created by flame on 2018/2/1.
 */
@Module
class ActivityModule(private val activity:AppCompatActivity){

    @ActivityScope
    @Provides
    fun provideHomeAdapter():CommonAdapter<Girl>{
        return CommonAdapter(R.layout.viewholder_girl) { h, _, d ->
            h.get<TextView>(R.id.des_view).text=d.desc
            Glide.with(activity).load(d.url).fitCenter().into(h.get(R.id.image_view))
            h.itemView.setOnClickListener({ activity.openActivity(BehaviorActivity::class.java)})
        }
    }

}
