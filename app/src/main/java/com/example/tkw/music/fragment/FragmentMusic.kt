package com.example.tkw.music.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tkw.music.ActivityMain
import com.example.tkw.music.R
import com.example.tkw.music.SongListUtil
import com.example.tkw.music.adapter.SongListAdapter

class FragmentMusic: androidx.fragment.app.Fragment(){

    private val url = "https://api.bzqll.com/music/tencent/hotSongList?key=579621905&categoryId=10000000&sortId=3&limit=60"

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var songListAdapter:SongListAdapter

    private val handler = Handler(Looper.getMainLooper())
    // private var songList = ArrayList<SongListTransform>()
    private var views:View? = null
    private var parent:ViewGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(views != null){
            if(views!!.parent != null){
                parent = views!!.parent as ViewGroup
                parent!!.removeView(views)
            }
            Log.d("view1_music",views.toString())
            return views
        }
        val views_ = inflater.inflate(R.layout.fragment_music,container,false)
        views = views_
        setSongListRecyclerView(views_)
        Log.d("view2_music",views_.toString())
        return views_
    }

    private fun setSongListRecyclerView(view: View){
        recyclerView = view.findViewById(R.id.music_song_list_recycler)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(view.context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false) as androidx.recyclerview.widget.RecyclerView.LayoutManager
        val songListUtil = SongListUtil()
        songListUtil.getSongList(url){
            //Log.d("AAA", it.size.toString())
            songListAdapter = SongListAdapter(it,view.context,callBackId)
            //songList.addAll(it)
            handler.post { recyclerView.adapter = songListAdapter  }
//            songListAdapter.notifyDataSetChanged()
            songListAdapter
        }
    }

    private val callBackId = object :SongListAdapter.CallBackId{
        override fun getId(id: String,bitmap:Bitmap) {
            val  fragmentSongList = FragmentSongList()
            val bundle = Bundle()
            bundle.putString("id",id)
            bundle.putParcelable("bitmap",bitmap)
            fragmentSongList.arguments = bundle
            Log.d("SSS",id.toString())
            val pareActivity = activity as ActivityMain
          //  pareActivity.toFragment(R.id.frame_layout_fragment,fragmentSongList,true)
        }
    }
}