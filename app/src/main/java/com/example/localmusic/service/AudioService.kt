package com.example.localmusic.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.localmusic.model.AudioBean
import de.greenrobot.event.EventBus
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

        //START_STICKY   粘性的  service强制杀死之后 会尝试重新启动service 不会传递原来的intent(null)
        //START_NOT_STICKY 非粘性的 service强制杀死之后 不会尝试重新启动service
        //START_REDELIVER_INTENT service强制杀死之后 会尝试重新启动service  会传递原来的intent
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    inner class AudioBinder : Binder(),Iservice,MediaPlayer.OnPreparedListener{

        /**
         * 跳转到当前进度播放
         */
        override fun seekTo(p1: Int) {
            mediaPlayer?.seekTo(p1)
        }
        /**
         * 获取当前播放进度
         */
        override fun getProgress(): Int? {
            return mediaPlayer?.currentPosition ?: 0
        }


        /**
         * 获取总进度
         */
        override fun getDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }




        //更新播放状态
        override fun updatePlayState() {
            //获取当前播放状态
            val isPlaying = isPlaying()
            //切换播放状态
            isPlaying?.let {
                if (isPlaying) {
                    //播放 暂停
                    mediaPlayer?.pause()
                } else {
                    //暂停 播放
                    mediaPlayer?.start()
                }
            }
        }


        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }
        override fun onPrepared(p0: MediaPlayer?) {
            mediaPlayer?.start()

            //通知界面更新
            notifyUpdateUi()
        }


        /**
         * 通知界面更新
         */
        private fun notifyUpdateUi() {
            //发送端
            EventBus.getDefault().post(list?.get(position))
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