package com.example.flame.kotlinstudy.ui

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.flame.kotlinstudy.App
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.databinding.ActivityDataBindingBinding
import com.example.flame.kotlinstudy.di.module.ActivityModule
import com.example.flame.kotlinstudy.lib.DataBindingAdapter
import com.example.flame.kotlinstudy.model.Girl
import com.example.flame.kotlinstudy.viewmodel.GirlViewModel
import javax.inject.Inject

class DataBindingActivity : AppCompatActivity() {

    @Inject
    lateinit var data: GirlViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app= application as App
        app.mComponent.plus(ActivityModule(this)).inject(this)
        val binding:ActivityDataBindingBinding=DataBindingUtil.setContentView(this,R.layout.activity_data_binding)
        binding.recyclerView.adapter=DataBindingAdapter<Girl>(R.layout.viewholder_databinding_girl)
        binding.viewModel=data
        data.load()

    }
}
