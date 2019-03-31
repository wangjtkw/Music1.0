package com.example.tkw.music

object Analyse{

    inline fun <reified T:Any>analyse(byteArray: ByteArray, callback:(T)->Unit){
        val result = GsonUtil.gsonUtil<T>(byteArray)
        callback(result)
    }
}