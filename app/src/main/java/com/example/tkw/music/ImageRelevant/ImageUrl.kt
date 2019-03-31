package com.example.tkw.music.ImageRelevant

import com.example.tkw.music.runOnNewThread
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * 获取图片连接，返回list
 */

class ImageUrl{

    companion object {
        private  const val IMAGE_URL = "http://gank.io/api/searchData/%E7%A6%8F%E5%88%A9/10/1"
    }

    fun sendRequest( url: String = IMAGE_URL,callback: (images: List<Image>) -> Unit){
         runOnNewThread { sendRequestByNet(url,callback)  }

    }

    private fun sendRequestByNet(url:String = IMAGE_URL,callback:(images:List<Image>)->Unit){
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(url)
                    .build()
            val response = client.newCall(request).execute()
            if (response.body() != null){
                val list = parseJSON(response.body()!!.string())
                callback(list)
            }else{
                throw Exception("response.body is null")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun parseJSON(data:String):List<Image>{
        val images = ArrayList<Image>()
        try {
            val result = JSONObject(data).get("results") as JSONObject
            val resultArray = JSONArray(result.toString())
            for(i in 0 until resultArray.length()){
                val jsonObject = resultArray.getJSONObject(i)
                val image = Image(jsonObject.get("url").toString())
                images.add(image)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }finally {
            return images
        }
    }

}