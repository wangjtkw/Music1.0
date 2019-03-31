package com.example.tkw.music

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tkw.music.adapter.SongListAdapter

class FragmentMusic:Fragment(){

    private val url = "https://api.bzqll.com/music/tencent/hotSongList?key=579621905&categoryId=10000000&sortId=3&limit=60"

    private lateinit var recyclerView:RecyclerView

    private lateinit var songListAdapter:SongListAdapter

    private lateinit var fragmentSongList:FragmentSongList

    private lateinit var bundle: Bundle

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_music,container,false)
        setSongListRecyclerView(view)
        return view
    }

    private fun setSongListRecyclerView(view: View){
        recyclerView = view.findViewById(R.id.music_song_list_recycler)
        val manager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = manager
        val songListUtil = SongListUtil()
        songListUtil.getSongList(url){
            //Log.d("AAA", it.size.toString())
            songListAdapter = SongListAdapter(it,view.context){id ->
                fragmentSongList = FragmentSongList()
                bundle = Bundle()
                bundle.putString("id",id)
                fragmentSongList.arguments = bundle
                replaceFragment(fragmentSongList)
            }
            handler.post { recyclerView.adapter = songListAdapter}
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_fragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}