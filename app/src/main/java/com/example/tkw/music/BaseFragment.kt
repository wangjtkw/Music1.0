package com.example.tkw.music

import android.support.v4.app.Fragment

open class BaseFragment:Fragment(){

    protected var fragmentListener:((fragment:BaseFragment,t:Boolean)->Unit)? = null

    fun toFragmentListener(listener:(fragment:BaseFragment,t:Boolean)->Unit){
        this.fragmentListener = listener
    }
}