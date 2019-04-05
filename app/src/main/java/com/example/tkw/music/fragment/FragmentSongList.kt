package com.example.tkw.music.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tkw.music.*
import com.example.tkw.music.adapter.SongAdapter

class FragmentSongList: Fragment(){

    private lateinit var songAdapter: SongAdapter
    private lateinit var songListImage: ImageView
    private lateinit var songListName:TextView
    private lateinit var songListAuthor:TextView
    private lateinit var songListDesc:TextView
    private lateinit var songNum:TextView
    private lateinit var playAll: ConstraintLayout
    private lateinit var recyclerView:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_song_list,container,false)
        initView(view)
        initUI(view)
        return view
    }

    private fun initUI(view: View){
        val id = arguments!!.getString("id")
        val bitmap = arguments!!.getParcelable<Bitmap>("bitmap")
        val url = "https://api.bzqll.com/music/tencent/songList?key=579621905&id=$id"
        songListImage.setImageBitmap(bitmap)
        setSongListInfo(url)
    }

    private fun initView(view: View){
        songListImage = view.findViewById<ImageView>(R.id.song_list_image)
        songListName = view.findViewById<TextView>(R.id.song_list_name)
        songListAuthor = view.findViewById<TextView>(R.id.song_list_author)
        songListDesc = view.findViewById<TextView>(R.id.song_list_desc)
        recyclerView = view.findViewById(R.id.song_recycler)
        songNum = view.findViewById<TextView>(R.id.song_num)
        playAll = view.findViewById(R.id.play_all)
        recyclerView = view.findViewById(R.id.song_recycler)
    }

    @SuppressLint("WrongConstant")
    private fun setSongListInfo(url:String){
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false) as RecyclerView.LayoutManager
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
            androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(pareActivity).sendBroadcast(playThisIntent)
        }
    }
}