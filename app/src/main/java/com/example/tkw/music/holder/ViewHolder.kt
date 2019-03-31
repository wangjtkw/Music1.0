package com.example.tkw.music.holder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.tkw.music.R

class ViewHolder(val view:View):BaseHolder(view){

    private val image = view.findViewById<ImageView>(R.id.toolbar_music_text)

    override fun bind(vararg any: Any?) {
        view.setOnClickListener {
            event?.let {
               if(it(any)){
                   Log.d("Tag","true")
               }else{
                   Log.d("Tag","fa")
               }
            }
        }

    }

}