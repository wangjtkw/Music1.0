package com.example.tkw.music.ImageRelevant

import com.example.tkw.music.Analyse
import com.example.tkw.music.JSONToByteArray

/**
 * 获取图片连接，返回list
 */

class ImageUrl{
    private val IMAGE_URL = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"

    fun getUrl(callBack:(image:Image) -> Unit){
        JSONToByteArray.sendRequest(IMAGE_URL){
           Analyse.analyse<Image>(it){image ->
               callBack(image)
           }
        }
    }


//    fun sendRequest( url: String = IMAGE_URL,callback: (images: List<Image>) -> Unit){
//         runOnNewThread { sendRequestByNet(url,callback)  }
//
//    }
//
//    private fun sendRequestByNet(url:String = IMAGE_URL,callback:(images:List<Image>)->Unit){
//        try {
//            val client = OkHttpClient()
//            val request = Request.Builder()
//                    .url(url)
//                    .build()
//            val response = client.newCall(request).execute()
//            Log.d("aaa",response.body()!!.string())
//            if (response.body() != null){
//                val list = parseJSON(response.body()!!.string())
//                Log.d("aaa",list.size.toString())
//                callback(list)
//            }else{
//                throw Exception("response.body is null")
//            }
//        }catch (e:Exception){
//            e.printStackTrace()
//        }
//    }
//
//    private fun parseJSON(data:String):List<Image>{
//        val images = ArrayList<Image>()
//        try {
//            val result = JSONObject(data).get("results") as JSONObject
//            val resultArray = JSONArray(result.toString())
//            for(i in 0 until resultArray.length()){
//                val jsonObject = resultArray.getJSONObject(i)
//                val image = Image(jsonObject.get("url").toString())
//                images.add(image)
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }finally {
//            return images
//        }
//    }

}