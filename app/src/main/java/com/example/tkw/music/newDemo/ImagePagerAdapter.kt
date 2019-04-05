package com.example.tkw.music.newDemo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class ImagePagerAdapter(private val imageList:List<ImageView>?,private val context:Context): androidx.viewpager.widget.PagerAdapter(){

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun getCount(): Int {
        return imageList?.size?:0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (imageList!= null){
            container.addView(imageList[position])
            return imageList[position]
        }
        return ImageView(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (imageList!= null){
            container.removeView(imageList[position])
        }
    }
}