package com.example.tkw.music

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.tkw.music.ImageRelevant.Image
import com.example.tkw.music.ImageRelevant.LoadImage

class ImageAdapter( private var imageList:List<Image>,private val context: Context?):RecyclerView.Adapter<ImageAdapter.ViewHolder>(){

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var imageView:ImageView
        init {
            imageView = view.findViewById(R.id.image_item) as ImageView
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.image_item,p0,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(context != null){
            LoadImage.loadImage(context,imageList.get(position).url){
                holder.imageView.setImageBitmap(it)
            }
        }else{
            throw Exception("the context is null")
        }
    }

        override fun getItemCount(): Int {
            return imageList.size
        }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

