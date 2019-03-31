package com.example.tkw.music.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(private val fragmentManager: FragmentManager,private val list:List<Fragment>):FragmentPagerAdapter(fragmentManager){
    override fun getItem(p0: Int): Fragment {
        return list[p0]
    }

    override fun getCount(): Int {
        return list.size
    }

}