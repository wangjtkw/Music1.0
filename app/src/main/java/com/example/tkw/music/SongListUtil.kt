package com.example.tkw.music

class SongListUtil{

    private val url_ = "https://api.bzqll.com/music/tencent/hotSongList?key=579621905&categoryId=10000000&sortId=3&limit=60"

    fun getSongList(url:String = url_,callback:(List<SongListTransform>)->Unit){
        JSONToByteArray.sendRequest(url){
            Analyse.analyse<HotSongList>(it){ hotSongList ->
                songListTransform(hotSongList){songListTransform ->
                    //Log.d("AAA",songListTransform.get(1).toString())
                    callback(songListTransform)
                }
            }
        }
    }

    private fun songListTransform(hotSongList: HotSongList, callback: (songListTransform:List<SongListTransform>) -> Unit){
        val hotSongListData = hotSongList.hotSongListData
        var i = 0;
        val songListTransform = ArrayList<SongListTransform>()
        var temp: SongListTransform?=null
        while (i < hotSongListData.size){
            if(i % 2 == 0){
                temp = SongListTransform()
                hotSongListData[i].apply {
                    temp.apply {
                        createTime1 = createTime
                        creator1 = creator
                        id1 = id
                        name1 = name
                        pic1 = pic
                        playCount1 = playCount

                    }
                }
            }else{
                hotSongListData[i].apply {
                    temp!!.apply {
                        createTime2 = createTime
                        creator2 = creator
                        id2 = id
                        name2 = name
                        pic2 = pic
                        playCount2 = playCount
                    }
                }
                songListTransform.add(temp!!)
            }
            i++
        }
        callback(songListTransform)
    }
}