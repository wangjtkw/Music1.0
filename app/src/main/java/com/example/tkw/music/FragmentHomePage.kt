package com.example.tkw.music

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tkw.music.adapter.ViewPagerAdapter

class FragmentHomePage:Fragment(),ViewPager.OnPageChangeListener{


    private lateinit var viewPagerAdapter:ViewPagerAdapter
    private lateinit var music_text:TextView
    private lateinit var image_text:TextView
    private var fragmentList = ArrayList<Fragment>()
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_page,container,false)
        init(view)
        return view
    }

    private fun init(view: View){
        music_text = view.findViewById<TextView>(R.id.toolbar_music_text)
        music_text.setTextColor(Color.parseColor("#ffffff"))
        music_text.setOnClickListener {
            viewPager.currentItem = 0
        }
        image_text = view.findViewById<TextView>(R.id.toolbar_image_text)
        image_text.setOnClickListener{
            viewPager.currentItem = 1
        }
        viewPager = view.findViewById<ViewPager>(R.id.home_view_pager)
        fragmentList.add(FragmentMusic())
        fragmentList.add(FragmentImage())
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager,fragmentList)
        viewPager.adapter = viewPagerAdapter


    }

    override fun onPageScrollStateChanged(p0: Int) {
        resetColor()
        when(p0){
            0 ->music_text.setTextColor(context!!.resources.getColorStateList(R.color.white))
            1 ->image_text.setTextColor(context!!.resources.getColorStateList(R.color.white))
        }
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageSelected(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun resetColor(){
        music_text.setTextColor(context!!.resources.getColorStateList(R.color.grey_white))
        image_text.setTextColor(context!!.resources.getColorStateList(R.color.grey_white))
    }

}
