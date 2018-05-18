package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.lib.MultiTypeAdapter
import kotlinx.android.synthetic.main.activity_text.*

class BehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_text)

        setSupportActionBar(toolbar)
        val items=Array(30,{i->"num$i"})

        val adapter= CommonAdapter<String>(android.R.layout.simple_list_item_1,{ holder, _, data ->
            holder.get<TextView>(android.R.id.text1).text=data

        })
        //adapter.addItems(items.asList(),false)

        val multiAdapter = MultiTypeAdapter(arrayOf(MultiTypeAdapter.HEADER,MultiTypeAdapter.FOOTER))

        multiAdapter.addItems(items.asList())

        co_recycler_view.adapter=multiAdapter

        multiAdapter.addHeader("header")
        multiAdapter.addFooter("footer")



    }
}
