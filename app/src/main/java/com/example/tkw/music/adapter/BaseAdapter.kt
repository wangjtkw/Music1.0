package com.example.tkw.music.adapter

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.tkw.music.holder.BaseHolder

open class BaseAdapter<T>(val onCreateViewHolderHandler:(p0: ViewGroup, p1: Int)->BaseHolder): androidx.recyclerview.widget.RecyclerView.Adapter<BaseHolder>(){

    protected val list = ArrayList<T>()
    protected val handler = Handler(Looper.getMainLooper())
    protected var event:((any:Any?)->Boolean)? = null


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = onCreateViewHolderHandler(p0,p1)

    fun notifyDataChange(list: List<T>){
        this.list.apply {
            clear()
            addAll(list)
        }
        handler.post { this.notifyDataSetChanged() }
    }

    override fun onBindViewHolder(baseHolder: BaseHolder, p1: Int) {
        baseHolder.bind(list[p1])
        baseHolder.clickEvent(event)
    }


    fun clickEvent(callback:(any:Any?)->Boolean){
        this.event = callback
    }

    companion object Factory {

        fun<T> getTypeAdapter(onCreateViewHolderHandler:(p0: ViewGroup, p1: Int) ->BaseHolder)= BaseAdapter<T>(onCreateViewHolderHandler)

    }


}