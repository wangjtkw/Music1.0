package com.example.tkw.music

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

class MyService : Service() {

    companion object {
        private const val DELETE_PENDINGINTENT_REQUEST = 2015
        private const val CONNECT_PENDINGINTENT_REQUEST = 2016
        private const val NEXT_PENDINGINTENT_REQUEST = 2017
        private const val PREVIOUS_PENDINGINTENT_REQUEST = 2018
        private const val CLOSE_PENDINGINTENT_REQUEST = 2019
        private const val PLAY_PENDINGINTENT_REQUEST = 2020
        private const val PAUSE_PENDINGINTENT_REQUEST = 2021
        private const val NOTIFICATION_PENDINGINTENT_ID = 1

        private const val SINGLE_CYCLE = 1
        private const val ALL_CYCLE  = 2
    }

    private lateinit var notificationManager:NotificationManager
    private lateinit var builder:NotificationCompat.Builder
    private lateinit var remoteViews: RemoteViews

    private var isPause:Boolean = false

    private val mediaPlayer = MediaPlayer()
    private val musicBinder = MusicBinder()
    private var isSetData:Boolean = false


    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId = "Music"
            val channelName = "音乐播放"
            val importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId,channelName,importance)
        }
        val intent = Intent(this,ActivityMain::class.java)
        val contentPendingIntent = PendingIntent.getActivity(this, CONNECT_PENDINGINTENT_REQUEST,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val delIntent = Intent(this,MyService::class.java)
        val delPendingIntent = PendingIntent.getService(this, DELETE_PENDINGINTENT_REQUEST,delIntent,PendingIntent.FLAG_UPDATE_CURRENT)


        remoteViews = RemoteViews(packageName,R.layout.notification)
        builder = NotificationCompat.Builder(this,"Music")
                .setSmallIcon(R.drawable.next)
                .setAutoCancel(false)
                .setContentIntent(contentPendingIntent)
                .setDeleteIntent(delPendingIntent)
                .setCustomBigContentView(remoteViews)

        startForeground(NOTIFICATION_PENDINGINTENT_ID,builder.build())

//        val playIntent = Intent("playChange")
//        //playIntent.putExtra("isPause",isPause)
//        val playChangePendingIntent = PendingIntent.getBroadcast(this, PLAY_PENDINGINTENT_REQUEST,playIntent,PendingIntent.FLAG_UPDATE_CURRENT)
//        remoteViews.setOnClickPendingIntent(R.id.notify_play,playChangePendingIntent)

        val playIntent = Intent("playChanged")
        val playChangePendingIntent = PendingIntent.getBroadcast(this, PLAY_PENDINGINTENT_REQUEST,playIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.notify_play,playChangePendingIntent)

        val previousIntent = Intent("previousMusic")
        val previousPendingIntent = PendingIntent.getBroadcast(this, PREVIOUS_PENDINGINTENT_REQUEST,previousIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.notify_previous,previousPendingIntent)

        val nextIntent = Intent("nextMusic")
        val nextPendingIntent = PendingIntent.getBroadcast(this, NEXT_PENDINGINTENT_REQUEST,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.notify_next,nextPendingIntent)

        val closeIntent = Intent("closeMusic")
        val closePendingIntent = PendingIntent.getBroadcast(this, CLOSE_PENDINGINTENT_REQUEST,closeIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.notify_close,closePendingIntent)

        initMediaPlayer()
    }




    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId:String,channelName: String,importance:Int){
        val channel = NotificationChannel(channelId,channelName,importance)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun initMediaPlayer(){
        mediaPlayer.setWakeMode(this,PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            isPause = false
        }
        mediaPlayer.setOnErrorListener { mp, what, extra ->
            mediaPlayer.reset()
            return@setOnErrorListener false
        }
        mediaPlayer.setOnCompletionListener {
            val finishIntent = Intent("musicFinished")
            sendBroadcast(finishIntent)
        }
    }

    private fun updateNotification(songName: String,songAuthor: String,bitmap: Bitmap){
        remoteViews.setImageViewResource(R.id.notify_play,R.drawable.notify_pause)
        remoteViews.setTextViewText(R.id.notify_song_name,songName)
        remoteViews.setTextViewText(R.id.notify_song_author,songAuthor)
        remoteViews.setImageViewBitmap(R.id.notify_image,bitmap)
        notificationManager.notify(NOTIFICATION_PENDINGINTENT_ID,builder.build())
    }

    override fun onBind(intent: Intent): IBinder {
        return MusicBinder()
    }


    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying){
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        notificationManager.cancel(NOTIFICATION_PENDINGINTENT_ID)
        stopForeground(true)
        stopSelf()
    }

    //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    inner class MusicBinder: Binder(){

        fun getService():Service{
            return this@MyService
        }

        fun play(){
            mediaPlayer.start()
            remoteViews.setImageViewResource(R.id.notify_play,R.drawable.notify_pause)
            notificationManager.notify(NOTIFICATION_PENDINGINTENT_ID,builder.build())
            isPause = false
            Log.d("play",isPause.toString())
        }

        fun pause(){
            mediaPlayer.pause()
            remoteViews.setImageViewResource(R.id.notify_play,R.drawable.notify_start)
            notificationManager.notify(NOTIFICATION_PENDINGINTENT_ID,builder.build())
            isPause = true
            Log.d("pause",isPause.toString())
        }

        fun playMusic(path:String,songName:String,songAuthor:String,bitmap: Bitmap){
            try {
                if (isPause){
                    mediaPlayer.start()
                    isPause = false
                }else{
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(path)
                    mediaPlayer.prepareAsync()
                    isPause = false
                    Log.d("playMusic",isPause.toString())
                }
                updateNotification(songName,songAuthor,bitmap)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        //歌曲时长
        fun getDuration():Int{
            return mediaPlayer.duration
        }
        //当前位置
        fun getPosition():Int{
            return mediaPlayer.currentPosition
        }
        //跳到指定位置
        fun setPosition(position:Int){
            mediaPlayer.seekTo(position)
        }
    }

}
