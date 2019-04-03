package com.example.tkw.music.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup

class ViewPagerAdapter(private val fragmentManager: FragmentManager,private val list:List<Fragment>):FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(p0: Int): Fragment {
        return list[p0]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }
}