package com.example.tkw.music.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tkw.music.R
import com.example.tkw.music.SongData
import com.example.tkw.music.holder.EmptyHolder
import com.example.tkw.music.holder.SongHolder

class SongAdapter(private var songList:List<SongData>, private val context: Context?,private val callback:(songList:List<SongData>,position:Int) ->Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        private const val FOOT_TYPE = 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, type: Int): RecyclerView.ViewHolder {
        if (type == SongAdapter.FOOT_TYPE){
            val view = LayoutInflater.from(p0.context).inflate(R.layout.z_blank_view,p0,false)
            return EmptyHolder(view)
        } else {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.song_item, p0, false)
            val holder = SongHolder(view)
            holder.songItem.setOnClickListener {
                callback(songList, holder.adapterPosition)
            }
            return holder
        }
    }

    override fun getItemCount(): Int {
        return songList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SongHolder){
            setSongList(holder,position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position >= songList.size){
            return SongAdapter.FOOT_TYPE
        }
        return super.getItemViewType(position)
    }

    private fun setSongList(holder: SongHolder,position: Int){
        holder.songName.text = songList[position].name
        holder.songAuthor.text = songList[position].singer
        holder.songNum.text = (position + 1).toString()
    }

}