package com.example.tkw.music

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

object JSONToByteArray {

    fun sendRequest(url: String, callback: (byteArray: ByteArray) -> Unit) {
        try {
            val client = OkHttpClient().newBuilder()
                    .connectTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .build()
            val request = Request.Builder()
                    .url(url)
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val byteArray = response.body()!!.bytes()
                    callback(byteArray)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }
}