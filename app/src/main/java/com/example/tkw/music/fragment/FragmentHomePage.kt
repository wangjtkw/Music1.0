//package com.example.tkw.music
//
//import android.graphics.Color
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v4.view.ViewPager
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.example.tkw.music.adapter.ViewPagerAdapter
//
//class FragmentHomePage:Fragment(),ViewPager.OnPageChangeListener{
//
//
//    private lateinit var viewPagerAdapter:ViewPagerAdapter
//    private lateinit var music_text:TextView
//    private lateinit var image_text:TextView
//    private var fragmentList = ArrayList<Fragment>()
//    private lateinit var viewPager: ViewPager
//    private var views:View? = null
//    private var parent:ViewGroup? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        if(views != null){
//            if(views!!.parent != null){
//                parent = views!!.parent as ViewGroup
//                parent!!.removeView(views)
//            }
//            Log.d("view1_HP",views.toString())
//            return views
//        }
//        val views_ = inflater.inflate(R.layout.fragment_home_page,container,false)
//        this.views = views_
//        init(views_)
//        Log.d("view2_HP",views_.toString())
//        return views_
//    }
//
//    private fun init(view: View){
//        music_text = view.findViewById(R.id.toolbar_music_text)
//        music_text.setTextColor(Color.parseColor("#ffffff"))
//        music_text.setOnClickListener {
//            viewPager.currentItem = 0
//            resetColor()
//            music_text.setTextColor(context!!.resources.getColorStateList(R.color.white))
//        }
//        image_text = view.findViewById(R.id.toolbar_image_text)
//        image_text.setOnClickListener{
//            viewPager.currentItem = 1
//            resetColor()
//            image_text.setTextColor(context!!.resources.getColorStateList(R.color.white))
//        }
//        viewPager = view.findViewById(R.id.home_view_pager)
//        if (fragmentList.size <= 1){
//            fragmentList.add(FragmentMusic())
//            fragmentList.add(FragmentImage())
//        }
//        viewPagerAdapter = ViewPagerAdapter(childFragmentManager,fragmentList)
//        viewPager.adapter = viewPagerAdapter
//        viewPager.offscreenPageLimit = 11
//
//        viewPager.currentItem = 0
//
//    }
//
//    override fun onPageScrollStateChanged(p0: Int) {
//
//    }
//
//    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onPageSelected(p0: Int) {
//        when(p0){
//            0 -> {
//                resetColor()
//                music_text.setTextColor(context!!.resources.getColorStateList(R.color.white))
//            }
//            1 -> {
//                resetColor()
//                image_text.setTextColor(context!!.resources.getColorStateList(R.color.white))
//            }
//        }
//    }
//
//    private fun resetColor(){
//        music_text.setTextColor(context!!.resources.getColorStateList(R.color.grey_white))
//        image_text.setTextColor(context!!.resources.getColorStateList(R.color.grey_white))
//    }
//
//}
