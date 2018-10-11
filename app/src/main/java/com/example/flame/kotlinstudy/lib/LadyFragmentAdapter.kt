package com.example.flame.kotlinstudy.lib

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log

/**
 * Created by Administrator on 2016/8/13.
 */
class LadyFragmentAdapter(fm: FragmentManager, internal var type: Int) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {

        return titles[type][position]
    }

    override fun getCount(): Int {
        return titles[type].size
    }

    override fun getItem(position: Int): Fragment {
        return null;
    }

    override fun getItemId(position: Int): Long {
        return (type * 10 + position).toLong()
    }

    companion object {

        private val titles = arrayOf(arrayOf("最新", "最热", "推荐"), arrayOf("性感妹子", "日本妹子", "台湾妹子", "清纯妹子"), arrayOf("视觉", "妹子", "名站写真"))
        private val paths = arrayOf(arrayOf("/", "/hot", "/best"), arrayOf("/xinggan", "/japan", "/taiwan", "/mm"), arrayOf("/zhuanti/1", "/zhuanti/2", "/zhuanti/3"))
    }
}
