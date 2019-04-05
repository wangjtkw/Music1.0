package com.example.tkw.music

import android.content.*
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tkw.music.ImageRelevant.ImageCondense


class ActivityMain : AppCompatActivity(),View.OnClickListener{

    private lateinit var myService: MyService
    private var isBindService = false
    private lateinit var navController: NavController


    private var view:View? = null

    private lateinit var localBroadcastManager: androidx.localbroadcastmanager.content.LocalBroadcastManager

    private var playList:List<SongData> ? = null
    private var position = -1

    private lateinit var musicPlay:RelativeLayout
    private lateinit var songImage:ImageView
    private lateinit var songName:TextView
    //    private lateinit var songWord:TextView
    private lateinit var songState:ImageView
    private var isFirst = true

    private val notional = SongData("004eEyQ52F2ukc","https://api.bzqll.com/music/tencent/lrc?id=004eEyQ52F2ukc&key=579621905"
            ,"中华人民共和国国歌-义勇军进行曲","https://api.bzqll.com/music/tencent/pic?id=004eEyQ52F2ukc&key=579621905"
            ,"群星",132,"https://api.bzqll.com/music/tencent/url?id=004eEyQ52F2ukc&key=579621905")

    private var isPause = false

    private val localReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "playThis" ->  playSong(position)
            }
        }
    }

    private val playerReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "previousMusic" -> playPrevious() //通知栏上一首
                "playChanged" -> playChange()
                "musicFinished" -> playNext()      //通知栏播放完毕
                "nextMusic" -> playNext()          //通知栏下一首


            }
        }
    }

    fun playChange(){
        Log.d("isPause","aaaa")
       //val state =  intent.getBooleanExtra("isPause",false)

        //Log.d("isPause",state.toString())
//        if(state){
//            songState.setImageResource(R.drawable.pause)
//            myService.MusicBinder().play()
//        }else{
//            songState.setImageResource(R.drawable.play)
//            myService.MusicBinder().pause()
//        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.song_state ->
                if(isFirst){
                    playSong()
                    isFirst = false
                }else{
                    if(isPause){
                        songState.setImageResource(R.drawable.pause)
                        myService.MusicBinder().play()
                        isPause = false
                    }else{
                        songState.setImageResource(R.drawable.play)
                        myService.MusicBinder().pause()
                        isPause = true
                    }
                }
        }
    }

    val sConnect = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBindService = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyService.MusicBinder
            myService = myBinder.getService() as MyService
            isBindService = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this,R.id.main_fragment)
        initView()
        bindService()
        startMusicService()
        val intentFilter = IntentFilter()
        intentFilter.addAction("previousMusic")
        intentFilter.addAction("nextMusic")
        intentFilter.addAction("musicFinished")
        intentFilter.addAction("beginPlay")
        intentFilter.addAction("beginPause")
        registerReceiver(playerReceiver,intentFilter)
        localBroadcastManager = androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this)
        val localIntentFilter = IntentFilter()
        localIntentFilter.addAction("playThis")
        localBroadcastManager.registerReceiver(localReceiver,localIntentFilter)
        songState.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
    private fun startMusicService(){
        val intent = Intent(this@ActivityMain, MyService::class.java)
        //传状态给service
        startService(intent)
    }

    private fun initView(){
        musicPlay = findViewById(R.id.music_play)
        songImage = findViewById(R.id.song_image)
        songName = findViewById(R.id.song_name)
//        songWord = findViewById(R.id.song_word)
        songState = findViewById(R.id.song_state)
    }

    fun playNext(){
        if(playList != null){
            if(position != playList!!.size - 1){
                position += 1
                playSong(position)
            }else{
                position = 0
                playSong(position)
            }
        }else{
            //toast
        }
    }

    fun playPrevious(){
        if(playList != null){
            if(position != 0){
                position -= 1
                playSong(position)
            }else{
                position = playList!!.size - 1
                playSong(position)
            }
        }else{
            //toast
        }
    }

    private fun playSong(ps:Int){
        if(playList != null){
            playSong(playList!![ps])
        }
    }

    private fun playSong(songData: SongData = notional){
        var bitmap:Bitmap
        songData.apply {
            JSONToByteArray.sendRequest(pic){
                bitmap = ImageCondense.condenseFromInputStream(it)
                myService.MusicBinder().playMusic(url,name,singer,bitmap)
                Handler(Looper.getMainLooper()).post {
                    songImage.setImageBitmap(bitmap)
                    songName.text = name
                    songState.setImageResource(R.drawable.pause)
                }
            }
        }
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(playerReceiver)
        localBroadcastManager.unregisterReceiver(localReceiver)
        unBind()
    }

    fun setData(songData:List<SongData>, position:Int){
        this.position = position
        this.playList = songData
    }

    //绑定服务
    private fun bindService(){
        if(!isBindService){
            val serviceIntent = Intent(this@ActivityMain,MyService::class.java)
            bindService(serviceIntent,sConnect,Context.BIND_AUTO_CREATE)
            isBindService = true
        }
    }

    //解除绑定
    private fun unBind(){
        if(isBindService){
            unbindService(sConnect)
            isBindService = false
        }
    }



}
