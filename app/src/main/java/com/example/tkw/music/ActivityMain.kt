package com.example.tkw.music

import android.content.*
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.tkw.music.ImageRelevant.ImageCondense
import com.example.tkw.music.ImageRelevant.ImageUrl
import com.example.tkw.music.adapter.BaseAdapter
import com.example.tkw.music.holder.ViewHolder



class ActivityMain : AppCompatActivity(),View.OnClickListener{

    private lateinit var myService: MyService
    private var isBindService = false

    private lateinit var localBroadcastManager:LocalBroadcastManager

    private var playList:List<SongData> ? = null
    private var position = -1

    private lateinit var musicPlay:RelativeLayout
    private lateinit var songImage:ImageView
    private lateinit var songName:TextView
    private lateinit var songWord:TextView
    private lateinit var songState:ImageView

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
                "nextMusic" -> playNext()          //通知栏下一首
                "musicFinished" -> playNext()      //通知栏播放完毕
                "beginPlay" ->  {
                    songState.setImageResource(R.drawable.pause)
                    myService.MusicBinder().play()
                }
                "beginPause" -> {
                    songState.setImageResource(R.drawable.play)
                    myService.MusicBinder().pause()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.song_state ->
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
        //*************
        adapter.clickEvent {
            true
        }
        adapter.notifyDataChange(ArrayList())
        //****************
        init()
        //**********
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
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        val localIntentFilter = IntentFilter()
        localIntentFilter.addAction("playThis")
        localBroadcastManager.registerReceiver(localReceiver,localIntentFilter)
        songState.setOnClickListener(this)
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
        songWord = findViewById(R.id.song_word)
        songState = findViewById(R.id.song_state)
    }

    public fun playNext(){
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

    public fun playPrevious(){
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
            var bitmap:Bitmap
            playList!![ps].apply {
                JSONToByteArray.sendRequest(pic){
                    bitmap = ImageCondense.condenseFromInputStream(it)
                    myService.play(url,name,singer,bitmap)
                    Handler(Looper.getMainLooper()).post {
                        songImage.setImageBitmap(bitmap)
                        songName.text = name
                        songState.setImageResource(R.drawable.pause)
                    }
                }
            }
            isPause = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(playerReceiver)
        localBroadcastManager.unregisterReceiver(localReceiver)
        unBind()
    }

    public fun setData(songData:List<SongData>, position:Int){
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

    /**
     * 切换fragment
     */
    private fun init(){
        replaceFragment(R.id.frame_layout_fragment,FragmentHomePage())
    }

    private fun replaceFragment(id:Int,fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(id,fragment)
            commit()
        }
    }

    /**
     * recycler后期
     */
    private val adapter = BaseAdapter<ImageUrl>{parent,viewType->
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item,parent,false)
        ViewHolder(view)
    }


}