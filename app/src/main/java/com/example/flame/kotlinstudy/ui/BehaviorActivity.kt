package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.flame.kotlinstudy.R
import kotlinx.android.synthetic.main.popupwindow_cart_chose.*

class BehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popupwindow_cart_chose)
        close_view.setOnClickListener({onBackPressed()})

    }
}
