package com.example.flame.kotlinstudy.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.LadyFragmentAdapter
import kotlinx.android.synthetic.main.fragment_content.*


class ContentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_content, container, false)
       // val type =.getIntExtra(Constants.TYPE, Constants.HOME)
        val adapter = LadyFragmentAdapter(childFragmentManager, 0)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
        return view
    }
}
