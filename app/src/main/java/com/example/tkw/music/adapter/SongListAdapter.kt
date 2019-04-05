package com.example.tkw.music.adapter

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tkw.music.ImageRelevant.LoadImage
import com.example.tkw.music.R
import com.example.tkw.music.SongListTransform
import com.example.tkw.music.holder.EmptyHolder
import com.example.tkw.music.newDemo.SongListHolder

class SongListAdapter(private var songListList:List<SongListTransform>, private val context: Context?, private val callBack:CallBackId): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>(){

    private var callBackView:CallBackView? = null

    companion object {
        private const val FOOT_TYPE = 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, type: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        if (type == FOOT_TYPE){
            val view = LayoutInflater.from(p0.context).inflate(R.layout.z_blank_view,p0,false)
            return EmptyHolder(view)
        } else{
            val view = LayoutInflater.from(p0.context).inflate(R.layout.song_list_item,p0,false)
            val holder = SongListHolder(view)
//            holder.songListItem1.setOnClickListener {
//                callBackView?.getView(view)
//            }
            return holder
        }
    }

    override fun getItemCount(): Int {
        return (songListList.size + 1)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if(holder is SongListHolder){
            if(Looper.getMainLooper() == Looper.myLooper()){
                setSongList(holder,position)
            }else{
                Handler(Looper.getMainLooper()).post {
                    setSongList(holder, position)
                }
            }
            if(context != null){
                holder.songListImage1.tag = songListList[position].pic1
                holder.songListImage2.tag = songListList[position].pic2
                LoadImage.loadImage(context, songListList[position].pic1){bitmap ->

                    holder.songListItem1.setOnClickListener {
                        callBack.getId(songListList[position].id1,bitmap)
                    }

                    holder.songListImage1.apply {
                        if(tag == songListList[position].pic1)
                        {
                            setImageBitmap(bitmap)
                        }
                    }
                }
                LoadImage.loadImage(context, songListList[position].pic2){bitmap ->

                    holder.songListItem2.setOnClickListener {
                        callBack.getId(songListList[position].id2,bitmap)
                    }

                    holder.songListImage2.apply {
                        if(tag == songListList[position].pic2){
                            setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }
    }

    interface CallBackView{
        fun getView(view: View)
    }

    interface CallBackId{
        fun getId(id:String,bitmap:Bitmap)
    }

    override fun getItemViewType(position: Int): Int {
        if(position >= songListList.size){
            return FOOT_TYPE
        }
        return super.getItemViewType(position)
    }

    private fun setSongList(holder: SongListHolder, position: Int){
        holder.songListName1.text = songListList[position].name1
        holder.songListName2.text = songListList[position].name2
        holder.songListAuthor1.text = songListList[position].creator1
        holder.songListAuthor2.text = songListList[position].creator2
    }


}