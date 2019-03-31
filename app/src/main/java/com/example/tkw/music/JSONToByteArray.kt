package com.example.tkw.music

import okhttp3.OkHttpClient
import okhttp3.Request

object JSONToByteArray{

    fun sendRequest(url:String,callback:(byteArray:ByteArray)->Unit){
        runOnNewThread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                        .url(url)
                        .build()
                val response = client.newCall(request).execute()
                if(response.body() != null){
                    val byteArray = response.body()!!.bytes()
                    callback(byteArray)
                }else{
                    throw Exception("the ByteArray is null")
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

    }
}