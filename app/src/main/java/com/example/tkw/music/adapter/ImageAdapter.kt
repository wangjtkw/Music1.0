package com.example.tkw.music.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.tkw.music.ImageRelevant.LoadImage
import com.example.tkw.music.ImageRelevant.Result
import com.example.tkw.music.R

class ImageAdapter( private var imageList:List<Result>,private val context: Context?): androidx.recyclerview.widget.RecyclerView.Adapter<ImageAdapter.ViewHolder>(){

    inner class ViewHolder(view:View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
        var imageView:ImageView = view.findViewById(R.id.image_item) as ImageView
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.image_item,p0,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(context != null){
            LoadImage.loadImage(context, imageList[position].url){
                holder.imageView.setImageBitmap(it)
            }
        }else{
            throw Exception("the context is null")
        }
    }

        override fun getItemCount(): Int {
            return imageList.size
        }
}

