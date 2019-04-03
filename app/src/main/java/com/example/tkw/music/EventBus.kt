package com.example.tkw.music

import kotlin.jvm.functions.FunctionN

object EventBus{

    val busMap = HashMap<String,ArrayList<Any?>>()

    fun subscribe(name:String,callback:FunctionN<Any?>){
        val list = busMap[name]?: ArrayList<Any?>()
        list.add(callback)
    }

    fun sendEvent(name: String,vararg p:Any?){
        val list = busMap[name]?:return
        val lastIndex = list.size -1
        for (i in  lastIndex downTo 0){
            val s = list[i]
            if (s is FunctionN<Any?>){
                s(p)
            }
        }
    }
}