package com.example.tkw.music

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtil{

   inline fun <reified T:Any> gsonUtil(byteArray: ByteArray):T{
       val types = object :TypeToken<T>(){}.type
       return Gson().fromJson<T>(String(byteArray),types)
    }
}
