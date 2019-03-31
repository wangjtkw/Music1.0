package com.example.tkw.music.ImageRelevant

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

object FileCache {

    fun get(context: Context, key: String, callback: (bitmap: Bitmap?) -> Unit) {
        try {
            val fileName = context.externalCacheDir.name + key
            if (File(fileName).exists()) {
                callback(BitmapFactory.decodeFile(fileName))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun put(context: Context, key: String, bitmap: Bitmap) {
        var fileOutputStream: FileOutputStream? = null
        try {
            val file = File(context.externalCacheDir, key)
            if (!file.exists()) {
                fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

