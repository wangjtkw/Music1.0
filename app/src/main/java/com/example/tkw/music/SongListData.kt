package com.example.tkw.music

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 歌曲播放地址
 * https://api.bzqll.com/music/tencent/url?key=579621905&id=001fXNWa3t8EQQ&br=
 * id：歌曲id
 * br：音质（默认96）
 * 48(标准) 96（高清） 320（高品质） flac(无损音质) ape（压缩无损音质）
 */

/**
 * 歌词链接
 * https://api.bzqll.com/music/tencent/lrc?key=579621905&id=001fXNWa3t8EQQ
 * id:歌曲id
 */

/**
 * 音乐图片链接
 * https://api.bzqll.com/music/tencent/pic?key=579621905&id=001fXNWa3t8EQQ
 * id:歌曲id（默认最大图）
 */

/**
 * 获取MV链接
 * https://api.bzqll.com/music/tencent/mvUrl?key=579621905&id=m00238resnh&r=4
 * id：MV的id
 * r：视频格式（默认3：720）
 * 视频大小类型：1:360 2：480 3:720 4:1080
 */

/**
 * 歌单请求数据
 *  https://api.bzqll.com/music/tencent/songList?key=579621905&id=1147906982
 *  id为歌单id
 */

data class SongList(
        @SerializedName("data")
        val songListData: SongListData,
        val result: String

)

data class SongListData(
        /**
         * author:歌单创建者
         * desc：歌单简介
         * id：歌单id
         * logo：歌单图片
         * songNum：歌单歌曲数量
         * songData：歌曲列表
         * title：歌单名称
         */
        val author: String,
        val desc: String,
        val id: String,
        val logo: String,
        @SerializedName("songnum")
        val songNum: String,
        @SerializedName("songs")
        val songData: List<SongData>,
        val title: String
)

data class SongData(
        /**
         * id：歌曲id
         * lrc：歌词
         * name：歌曲名称
         * pic：歌曲图片链接（圆形）
         * singer：歌手
         * time：歌曲长度
         * url：歌曲链接
         */
        val id: String,
        val lrc: String,
        val name: String,
        val pic: String,
        val singer: String,
        val time: Int,
        val url: String
)

/**
 * 搜索歌曲选项数据
 * https://api.bzqll.com/music/tencent/search?key=579621905&s=123&limit=100&offset=0&type=song
 * s：搜索关键字
 * limit：搜索数量（非必选）
 * offset：搜索页数（非必选，默认第一页）
 * type：搜索类型
 * 1. 音乐搜索:type=song
 * 2. 专辑搜索:type=album
 * 3. 歌单搜索:type=list (QQ音乐限制歌单每页最多查询50条)
 * 4. MV搜索:type=mv
 * 5. 用户搜索:type=user
 * 6. 歌词搜索:type=lrc
 */

/**
 * 搜索单曲
 */
data class SearchSong(
        @SerializedName("data")
    val songData: List<SongData>,
        val result: String
)

data class SearchAlbum(
        @SerializedName("data")
    val searchAlbumData: List<SearchAlbumData>,
        val result: String
)

/**
 * 搜索专辑
 */
data class SearchAlbumData(
        /**
         * albumMID：专辑id
         * albumName：专辑名称
         * albumPic：专辑图片链接
         * publicTime：出版时间
         * song_count：专辑歌曲数量
         */
    val albumMID: String,
        val albumName: String,
        val albumPic: String,
        val publicTime: String,
        val singerID: Int,
        val singerMID: String,
        val singerName: String,
        val singerName_hilight: String,
        val singer_list: List<Singer>,
        val song_count: Int
)

data class Singer(
    val id: Int,
    val mid: String,
    val name: String,
    val name_hilight: String
)

/**
 * 搜索歌单
 */
data class SearchList(
        @SerializedName("data")
        val searchListData: List<SearchListData>,
        val result: String
)

data class SearchListData(
        val copyrightnum: Int,
        val createtime: String,
        val creator: Creator,
        val diss_status: Int,
        val dissid: String,
        val dissname: String,
        val docid: Int,
        val imgurl: String,
        val introduction: String,
        val listennum: Int,
        val score: Double,
        val song_count: Int
)

data class Creator(
    val avatarUrl: String,
    val creator_uin: String,
    val encrypt_uin: String,
    val followflag: Int,
    val isVip: Int,
    val name: String,
    val qq: Int,
    val singerid: Int,
    val singermid: String,
    val type: Int
)

/**
 * 专辑详情数据
 * https://api.bzqll.com/music/tencent/album?key=579621905&id=000i3LVu28zIG2
 * id为专辑id
 */
data class Album(
        @SerializedName("data")
        val albumData: AlbumData,
        val result: String
)

data class AlbumData(
        /**
         * company：唱片公司
         * desc：专辑描述
         * id：专辑id
         * name：专辑名称
         * singer：专辑歌手
         * songData：歌曲列表
         */
    val company: String,
    val desc: String,
    val id: String,
    val name: String,
    val singer: String,
        @SerializedName("songs")
    val songData: List<SongData>
)

/**
 * 歌曲详情数据
 * https://api.bzqll.com/music/tencent/song?key=579621905&id=001fXNWa3t8EQQ
 * id为歌曲id
 */
data class Song(
        @SerializedName("data")
        val songData: SongData,
        val result: String
)

/**
 * 歌曲分类链接（只获取分类）
 * https://api.bzqll.com/music/tencent/songListCategory?key=579621905
 */
data class SongListCategory(
        @SerializedName("data")
        val songListCategoryData: List<SongListCategoryData>,
        val result: String
)

data class SongListCategoryData(
        /**
         * categoryGroupName:分类大名称
         * items：小分类数据
         */
    val categoryGroupName: String,
    val items: List<Item>
)

data class Item(
        /**
         * allSorts：所有排序种类
         * categoryId：小分类id
         *categoryName：小分类名称
         */
    val allSorts: List<AllSort>,
        val categoryId: Int,
        val categoryName: String
)

data class AllSort(
        /**
         * sortId：排序id
         * sortName：排序名称
         */
    val sortId: Int,
    val sortName: String
)

/**
 * 分类详情链接（可获取歌单id）
 * https://api.bzqll.com/music/tencent/hotSongList?key=579621905&categoryId=10000000&sortId=3&limit=60
 * categoryId：分类id（即小分类id）
 * sortId：排序id
 * limit：歌曲限制（默认60，最大60）
 */
data class HotSongList(
        @SerializedName("data")
    val hotSongListData: List<HotSongListData>,
        val result: String
)

data class HotSongListData(
        /**
         * createTime:创建时间
         * creator：创建者名称
         * id：歌单id
         * name：歌单名称
         * pic：歌单图片
         * playCount：听歌人数
         */
    val createTime: String,
    val creator: String,
    val id: String,
    val name: String,
    val pic: String,
    val playCount: String
)

data class SongListTransform(
        var createTime1:String = "",
        var creator1: String = "",
        var id1: String = "",
        var name1: String = "",
        var pic1: String = "",
        var playCount1: String = "",
        var createTime2: String = "",
        var creator2: String = "",
        var id2: String = "",
        var name2: String = "",
        var pic2: String = "",
        var playCount2: String = ""
) : Serializable



