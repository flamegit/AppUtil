package com.example.flame.kotlinstudy.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.CommonPagerAdapter
import kotlinx.android.synthetic.main.activity_behavior3.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class BehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_behavior3)

        val adapter=CommonPagerAdapter<String>{ v,s->
            val textView=TextView(v.context)
            textView.text=s
            textView.setBackgroundColor(Color.BLUE)
            textView
        }
        adapter.addItems(listOf("a","hello","fine","here","welcome"))
        view_pager.apply {
            pageMargin=100
            overScrollMode= View.OVER_SCROLL_NEVER
        }
        view_pager.adapter=adapter

        val job= launch(CommonPool) {
            delay(1000)
        }




    }

}
