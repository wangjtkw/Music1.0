package com.example.tkw.music.ImageRelevant

import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * 计算图片压缩率，并返回压缩后的bitmap
 */
object ImageCondense{

    private fun calculateSampleSize(options: BitmapFactory.Options, targetWidth:Int, targetHeight:Int):Int{
        val outWidth =  options.outWidth
        val outHeight = options.outHeight
        var sampleSize = 1
        while (outHeight / sampleSize > targetHeight || outWidth / sampleSize > targetWidth) {
            sampleSize *= 2
        }
        return sampleSize
    }

    fun condenseFromInputStream(byteArray: ByteArray, width:Int = 100, height:Int = 100): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
        val size = calculateSampleSize(options, width, height)
        options.inSampleSize = size
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }
}