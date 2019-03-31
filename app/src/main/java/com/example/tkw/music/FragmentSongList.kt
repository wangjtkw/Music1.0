package com.example.tkw.music

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tkw.music.ImageRelevant.LoadImage
import com.example.tkw.music.adapter.SongAdapter

class FragmentSongList():Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_song_list,container,false)
        val id = arguments!!.getString("id")
        val url = "https://api.bzqll.com/music/tencent/songList?key=579621905&id=$id"
        setSongListInfo(view,url)
        return view
    }

    private fun setSongListInfo(view: View,url:String){
        recyclerView = view.findViewById<RecyclerView>(R.id.song_recycler)
        val manager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = manager
        JSONToByteArray.sendRequest(url){
            Analyse.analyse<SongList>(it){songList ->
                setView(view,songList)
                songAdapter = SongAdapter(songList.songListData.songData,this.context){songData, position ->
                    val pareActivity = activity as ActivityMain
                    pareActivity.setData(songData,position)
                    val playThisIntent = Intent("playThis")
                    LocalBroadcastManager.getInstance(pareActivity).sendBroadcast(playThisIntent)
                }
                Handler(Looper.getMainLooper()).post {
                    recyclerView.adapter = songAdapter
                }
            }
        }
    }

    private fun setView(view: View,songList: SongList){
        val songListImage = view.findViewById<ImageView>(R.id.song_list_image)
        val songListName = view.findViewById<TextView>(R.id.song_list_name)
        val songListAuthor = view.findViewById<TextView>(R.id.song_list_author)
        val songListDesc = view.findViewById<TextView>(R.id.song_list_desc)
        LoadImage.loadImage(this.context!!,songList.songListData.logo){
            Handler(Looper.getMainLooper()).post {
                songListImage.setImageBitmap(it)
                songList.songListData.apply {
                    songListName.text = title
                    songListAuthor.text = author
                    songListDesc.text = desc
                }
            }
        }
    }
}