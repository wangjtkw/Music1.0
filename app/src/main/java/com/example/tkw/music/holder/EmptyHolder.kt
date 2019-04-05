package com.example.tkw.music.holder

import android.view.View
import android.widget.FrameLayout
import com.example.tkw.music.R

class EmptyHolder(view: View): BaseHolder(view){
    override fun bind(vararg any: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val empty = view.findViewById<FrameLayout>(R.id.empty)
}