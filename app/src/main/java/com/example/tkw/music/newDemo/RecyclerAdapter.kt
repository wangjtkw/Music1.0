package com.example.tkw.music.newDemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkw.music.R
import com.example.tkw.music.SongListTransform
import com.example.tkw.music.holder.BaseHolder
import com.example.tkw.music.holder.EmptyHolder

class RecyclerAdapter(): RecyclerView.Adapter<BaseHolder>(){

    private val TYPE_IMAGE = 0
    private val TYPE_MUSIC = 1
    private val TYPE_EMPTY = 2

    private val imageList = ArrayList<String>()
    private val songList = ArrayList<SongListTransform>()


    fun freshImageList(list:List<String>){
        imageList.clear()
        imageList.addAll(list)
    }

    fun freshSongList(list: List<SongListTransform>){
        songList.clear()
        songList.addAll(list)
    }

    override fun onCreateViewHolder(p0: ViewGroup, type: Int): BaseHolder {
        if (type == TYPE_IMAGE){
            val view = LayoutInflater.from(p0.context).inflate(R.layout.viewpager,p0,false)
            return ViewPagerHolder(view)
        }else if(type == TYPE_EMPTY){
            val view = LayoutInflater.from(p0.context).inflate(R.layout.z_blank_view,p0,false)
            return EmptyHolder(view)
        } else {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.song_list_item,p0,false)
            return SongListHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position_: Int) {
        when(getItemViewType(position_)){
            TYPE_IMAGE->holder.bind(imageList)
            TYPE_MUSIC->holder.bind(songList[position_ - 1])
        }
    }

    override fun getItemCount(): Int {
        return (songList.size+2)
    }

    override fun getItemViewType(position: Int) = when(position){
        0->TYPE_IMAGE
        songList.size + 1 -> TYPE_EMPTY
        else->TYPE_MUSIC
    }

}