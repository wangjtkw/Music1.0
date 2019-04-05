package com.example.tkw.music.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tkw.music.ImageRelevant.ImageUrl
import com.example.tkw.music.R
import com.example.tkw.music.SongListUtil
import com.example.tkw.music.newDemo.RecyclerAdapter


class FirstFragment(): Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private val  recyclerAdapter = RecyclerAdapter()
    private val songListUtil = SongListUtil()
    private  val imageUrl = ImageUrl()
    private val handler = Handler()
    private var view_:View? = null
    private var parent:ViewGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view_ != null){
            if (view_!!.parent != null){
                parent = view_!!.parent as ViewGroup
                parent!!.removeView(view_)
            }
            return view_
        }
        val views = LayoutInflater.from(this.context).inflate(R.layout.fragment_home_page, container, false)
        view_ = views
        initView(views)
        initData()
        return views
    }

    private fun initData(){
        freshSongList()
    }

    private fun initView(view: View) {
        toolbar = view.findViewById(R.id.first_toolbar)
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = recyclerAdapter
    }

    private fun freshImageList() {
        imageUrl.getUrl { image ->
            handler.post {
                recyclerAdapter.freshImageList(image.results.map { it.url })
                recyclerAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun freshSongList() {
        songListUtil.getSongList() {
            handler.post {
                recyclerAdapter.freshSongList(it)
                recyclerAdapter.notifyDataSetChanged()
                freshImageList()
            }
        }
    }
}


