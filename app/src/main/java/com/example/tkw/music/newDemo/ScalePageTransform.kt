package com.example.tkw.music.newDemo

import androidx.viewpager.widget.ViewPager
import android.view.View



class ScalePageTransform: androidx.viewpager.widget.ViewPager.PageTransformer{
    private val MAX_SCALE = 1.0f
    private val MIN_SCALE = 0.9f
    private val MIN_Alpha = 0.5f

    override fun transformPage(view: View, position: Float) {
        if(position < -1){
            view.scaleX(MIN_SCALE)
            view.scaleY(MIN_SCALE)
        }else if(position < 1){

        }
    }

}

private operator fun Float.invoke(minScale: Float) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

