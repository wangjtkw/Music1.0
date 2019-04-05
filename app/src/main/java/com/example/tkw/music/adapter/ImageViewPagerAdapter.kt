//package com.example.tkw.music.adapter
//
//import android.content.Context
//import android.support.v4.view.PagerAdapter
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.tkw.music.ImageRelevant.Result
//
//class ImageViewPagerAdapter(private val imageList:List<Result>,private val context:Context): PagerAdapter(){
//    override fun isViewFromObject(p0: View, p1: Any): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getCount(): Int {
//        return Int.MAX_VALUE
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        var position_ = position
//        position_ %= imageList.size
//        if(position_ < 0){
//            position_ = imageList.size + position
//        }
//        val view = LayoutInflater.from(context).inflate()
//
//        container.addView()
//    }
//}