package com.example.tkw.music

import android.util.Log
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 对链接进行加密
 */

object MD5Helper{

    fun keyFromUrl(url: String?): String {
        var cacheKey: String
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(url?.toByteArray())
            val byteArray = md5.digest()
            cacheKey = BigInteger(1,byteArray).toString(16)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            cacheKey = url?.hashCode().toString()
        }
        for (i in 0 until 32 - cacheKey.length){
            cacheKey = "0$cacheKey"
        }
        Log.d("aaa",cacheKey)
        return cacheKey
    }

}