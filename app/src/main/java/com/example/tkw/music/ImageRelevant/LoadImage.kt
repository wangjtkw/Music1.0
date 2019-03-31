package com.example.tkw.music.ImageRelevant

import android.content.Context
import android.graphics.Bitmap
import com.example.tkw.music.JSONToByteArray
import com.example.tkw.music.MD5Helper
import com.example.tkw.music.runOnNewThread

/**
 * 用于获取bitmap，对外调用，可加本地缓存
 */

object LoadImage {

    fun loadImage(context: Context,url:String,callback:(bitmap:Bitmap) -> Unit){
        runOnNewThread {
            val key = MD5Helper.keyFromUrl(url)
            MemoryCache.get(context,key){
                if(it != null){
                    callback(it)
                }else{
                    JSONToByteArray.sendRequest(url){ byteArray ->
                        runOnNewThread {
                            val bitmap = ImageCondense.condenseFromInputStream(byteArray)
                            MemoryCache.put(context,key,bitmap)
                            callback(bitmap)
                        }
                    }
                }
            }
        }
    }
}