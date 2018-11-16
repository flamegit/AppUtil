package com.example.flame.kotlinstudy.lib

import android.view.View
import android.view.ViewGroup

class CommonPagerAdapter<T>(val mFactory: (ViewGroup, T) -> View) : android.support.v4.view.PagerAdapter() {

    private val mContent: MutableList<T> = mutableListOf()
    var item: View? = null

    override fun getCount(): Int {
        return mContent.size
    }

    fun getContent(position:Int):T{
        return mContent[position]
    }

    fun addItems(lists: Collection<T>?) {
        if (lists == null || lists.isEmpty()) {
            return
        }
        mContent.clear()
        mContent.addAll(lists)
        notifyDataSetChanged()
    }

    fun addItem(item: T?) {
        item?.let {
            mContent.add(it)
            notifyDataSetChanged()
        }
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, any: Any) {
        super.setPrimaryItem(container, position, any)
        item = any as View
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = mFactory(container, mContent[position])
        container.addView(view)
        return view
    }

    //override fun getPageWidth(position: Int) =0.7f

}