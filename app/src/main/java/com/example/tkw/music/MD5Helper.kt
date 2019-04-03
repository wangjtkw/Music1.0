package com.example.tkw.music

import java.math.BigInteger
import java.security.MessageDigest

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
        } catch (e: Exception) {
            e.printStackTrace()
            cacheKey = url?.hashCode().toString()
        }
        for (i in 0 until 32 - cacheKey.length){
            cacheKey = "0$cacheKey"
        }
        return cacheKey
    }

}