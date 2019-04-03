package com.example.tkw.music

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tkw.music.adapter.SongAdapter

class FragmentSongList():Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var songAdapter: SongAdapter
    private lateinit var songListImage:ImageView
    private lateinit var songListName:TextView
    private lateinit var songListAuthor:TextView
    private lateinit var songListDesc:TextView
    private lateinit var songNum:TextView
    private lateinit var playAll:ConstraintLayout
    private var views:View? = null
    private var parent:ViewGroup? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(views != null){
            parent = views!!.parent as ViewGroup
            if(parent != null){
                parent!!.removeView(views)
            }
            Log.d("view1_song_list",views.toString())
            return views
        }
        val id = arguments!!.getString("id")
        val bitmap = arguments!!.getParcelable<Bitmap>("bitmap")
        val views_ = inflater.inflate(R.layout.fragment_song_list,container,false)
        views = views_
        initView(views_!!)
        songListImage.setImageBitmap(bitmap)
        val url = "https://api.bzqll.com/music/tencent/songList?key=579621905&id=$id"
        setSongListInfo(views_,url)
        Log.d("view2_song_list",views_.toString())
        return views_
    }

    private fun initView(view: View){
        songListImage = view.findViewById<ImageView>(R.id.song_list_image)
        songListName = view.findViewById<TextView>(R.id.song_list_name)
        songListAuthor = view.findViewById<TextView>(R.id.song_list_author)
        songListDesc = view.findViewById<TextView>(R.id.song_list_desc)
        recyclerView = view.findViewById(R.id.song_recycler)
        songNum = view.findViewById<TextView>(R.id.song_num)
        playAll = view.findViewById(R.id.play_all)

    }

    private fun setSongListInfo(view: View,url:String){
        recyclerView.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false) as RecyclerView.LayoutManager
        JSONToByteArray.sendRequest(url){
            Analyse.analyse<SongList>(it){songList ->
                setView(songList)
                songAdapter = SongAdapter(songList.songListData.songData,this.context,callBackSong)
                Handler(Looper.getMainLooper()).post {
                    recyclerView.adapter = songAdapter
                    (recyclerView.adapter as SongAdapter).notifyDataSetChanged()
                }
                playAll.setOnClickListener {
                    val pareActivity = activity as ActivityMain
                    pareActivity.setData(songList.songListData.songData,0)
                    val playThisIntent = Intent("playThis")
                    LocalBroadcastManager.getInstance(pareActivity).sendBroadcast(playThisIntent)
                }
            }
        }
    }

    private fun setView(songList: SongList){
        Handler(Looper.getMainLooper()).post{
            songNum.text = songList.songListData.songData.size.toString()
            songList.songListData.apply {
                songListName.text = title
                songListAuthor.text = author
                songListDesc.text = desc
            }
        }
    }

    private val callBackSong = object :SongAdapter.CallBackSong{
        override fun getSong(songList: List<SongData>, position: Int) {
            val pareActivity = activity as ActivityMain
            pareActivity.setData(songList,position)
            val playThisIntent = Intent("playThis")
            LocalBroadcastManager.getInstance(pareActivity).sendBroadcast(playThisIntent)
        }
    }
}