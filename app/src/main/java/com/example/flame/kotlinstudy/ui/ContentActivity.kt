package com.example.flame.kotlinstudy.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.LadyFragmentAdapter
import kotlinx.android.synthetic.main.fragment_content.*

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_content)
        val adapter = LadyFragmentAdapter(supportFragmentManager, 0)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }
}
