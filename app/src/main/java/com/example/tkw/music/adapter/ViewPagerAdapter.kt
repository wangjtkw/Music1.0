package com.example.tkw.music.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup

class ViewPagerAdapter(private val fragmentManager: androidx.fragment.app.FragmentManager, private val list:List<androidx.fragment.app.Fragment>): androidx.fragment.app.FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(p0: Int): androidx.fragment.app.Fragment {
        return list[p0]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return androidx.viewpager.widget.PagerAdapter.POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }
}