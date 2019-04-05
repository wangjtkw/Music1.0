package com.example.tkw.music.newDemo

import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.tkw.music.ImageRelevant.LoadImage
import com.example.tkw.music.R
import com.example.tkw.music.holder.BaseHolder


class ViewPagerHolder(private val view: View) : BaseHolder(view) {

    val handler = Handler()
    override fun bind(vararg any: Any?) {
        if (any.isEmpty()) {
            return
        }
        val list = any[0] as List<String>
        val imageList = ArrayList<ImageView>()
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        list.forEach {url->
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageList.add(imageView)
            LoadImage.loadImage(context,url){
                handler.post {
                    imageView.setImageBitmap(it)
                    Log.d("iamge--","load ")
                }
            }
        }
        val adapter = ImagePagerAdapter(imageList,context)
        viewPager.adapter = adapter
    }
}