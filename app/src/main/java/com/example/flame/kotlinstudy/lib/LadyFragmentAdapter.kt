package com.example.flame.kotlinstudy.lib

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.flame.kotlinstudy.model.Site
import com.example.flame.kotlinstudy.ui.GirlListFragment

/**
 * Created by Administrator on 2016/8/13.
 */
class LadyFragmentAdapter(fm: FragmentManager,var site: Site,var type:Int) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return site.getTitle(type)[position]
    }
    override fun getCount(): Int {
        return site.getTitle(type).size
    }
    override fun getItem(position: Int): Fragment {
        return GirlListFragment.newInstance(site.endUrl+site.getPath(type)[position])
    }
    override fun getItemId(position: Int): Long {
        return (site.siteType*100+type * 10 + position).toLong()
    }
}
