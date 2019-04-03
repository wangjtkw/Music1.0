package com.example.tkw.music

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tkw.music.ImageRelevant.ImageUrl
import com.example.tkw.music.ImageRelevant.Result
import com.example.tkw.music.adapter.ImageAdapter

class FragmentImage: Fragment(){

    private lateinit var recyclerView: RecyclerView
    private val imageUrl = ImageUrl()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var adapter: ImageAdapter
    private var imagesArray = ArrayList<Result>(10)
    private var views:View? = null
    private var parent:ViewGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(views != null){
            if(views!!.parent != null){
                parent = views!!.parent as ViewGroup
                parent!!.removeView(views)
            }
            Log.d("view1_image",views.toString())
            return views
        }
        val views_ = inflater.inflate(R.layout.fragment_image,container,false)
        views = views_
        setRecyclerView(views_)
        Log.d("view2_image",views_.toString())
        return views_
    }

    private fun setRecyclerView(view: View){
        recyclerView = view.findViewById(R.id.image_recycler)
        recyclerView.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)
        adapter = ImageAdapter(imageList = imagesArray, context = view.context)
        recyclerView.adapter = adapter
        imageUrl.getUrl { images ->
            imagesArray.addAll(images.results)
            handler.post { adapter.notifyDataSetChanged() }
        }
    }

}