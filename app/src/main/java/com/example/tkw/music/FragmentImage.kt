package com.example.tkw.music

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tkw.music.ImageRelevant.ImageUrl

class FragmentImage: Fragment(){

    private lateinit var recyclerView: RecyclerView
    private val imageUel = ImageUrl()
    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image,container,false)
        //setRecyclerView(view)
        return view
    }

    private fun setRecyclerView(view: View){
        recyclerView = view.findViewById(R.id.image_recycler)
        val manager = GridLayoutManager(view.context,1)
        manager.orientation = GridLayoutManager.HORIZONTAL
        recyclerView.layoutManager = manager
        var adapter:ImageAdapter? = null
        imageUel.sendRequest { images ->
            adapter?:ImageAdapter(images,requireContext()).also {
                adapter = it
                it.setHasStableIds(true)
            }
            handler.post { adapter?.notifyDataSetChanged() }
        }
    }


}