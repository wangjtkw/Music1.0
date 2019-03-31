package com.example.tkw.music.holder

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseHolder(view: View):RecyclerView.ViewHolder(view){

    protected val context = view.context!!
    protected var event:((any:Any?)->Boolean) ? = null

    abstract fun bind(vararg any: Any?)

   fun clickEvent(event:((any:Any?)->Boolean) ?){
       this.event = event
   }

}