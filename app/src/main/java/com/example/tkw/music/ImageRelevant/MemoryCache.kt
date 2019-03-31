package com.example.tkw.music.ImageRelevant

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache

object MemoryCache{

    private var myLruCache:LruCache<String,Bitmap>
    private val maxSize = Runtime.getRuntime().maxMemory()/8

    init {
        myLruCache = LruCache(maxSize.toInt())
    }

    fun put(context: Context,key: String, bitmap: Bitmap){
        FileCache.put(context,key,bitmap)
        myLruCache.put(key,bitmap)
    }

    fun get(context: Context,key: String, callback: (bitmap: Bitmap?) -> Unit) {
        var bitmap:Bitmap? = null
        bitmap = myLruCache.get(key)
        if(bitmap == null){
            FileCache.get(context,key){
                if(it != null){
                    put(context, key, it)
                }
            }
        }
        callback(bitmap)
    }

}