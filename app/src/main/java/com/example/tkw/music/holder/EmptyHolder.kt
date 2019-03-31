package com.example.tkw.music.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.example.tkw.music.R

class EmptyHolder(view: View):RecyclerView.ViewHolder(view){
    val empty = view.findViewById<FrameLayout>(R.id.empty)
}