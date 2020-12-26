package com.example.localmusic.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.localmusic.model.AudioBean
import java.util.ArrayList

class AudioService :Service(){

    var mediaPlayer: MediaPlayer? = null
    var list: ArrayList<AudioBean>? = null
    var position: Int= 0 // 正在播放的position
    var manager: NotificationManager? = null
    var notification: Notification? = null

    val FROM_PRE= 1
    val FROM_NEXT= 2
    val FROM_STATE= 3
    val FROM_CONTENT= 4
    val binder by lazy { AudioBinder() }


    override fun onCreate() {
        super.onCreate()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //获取集合以及position
        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
        position =intent?.getIntExtra("position",-1)?:-1
        //开始播放音乐
        binder.playItem()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    inner class AudioBinder : Binder(),MediaPlayer.OnPreparedListener{

        override fun onPrepared(p0: MediaPlayer?) {
            mediaPlayer?.start()
        }
        fun playItem(){
            mediaPlayer =MediaPlayer()
            mediaPlayer?.let {
                it.setOnPreparedListener(this)
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
            }

        }


    }
}