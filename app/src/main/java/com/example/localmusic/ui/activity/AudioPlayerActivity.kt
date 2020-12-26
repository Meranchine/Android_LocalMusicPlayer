package com.example.localmusic.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.IBinder
import com.example.localmusic.R
import com.example.localmusic.base.BaseActivity
import com.example.localmusic.model.AudioBean
import com.example.localmusic.service.AudioService

class AudioPlayerActivity: BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    val conn by lazy { AudioConnection() }
    override fun initData() {
        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
        val position = intent.getIntExtra("position",-1)
        //通过audioservice播放音乐
        val intent=Intent(this,AudioService::class.java)
        //通过intent将list以及position传递过去
        intent.putExtra("list",list)
        intent.putExtra("position",position)
        //先开启
        startService(intent)
        //再绑定
        bindService(intent,conn, Context.BIND_AUTO_CREATE)

//        //播放音乐
//        val mediaPlayer =MediaPlayer()
//        mediaPlayer.setOnPreparedListener(){
//            //开始播放
//            mediaPlayer.start()
//        }
//        if (list != null) {
//            mediaPlayer.setDataSource(list.get(position).data)
//        }
//        mediaPlayer.prepareAsync()

    }

    inner class AudioConnection : ServiceConnection {

        //service连接时
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
//            iService = p1 as Iservice
        }

          //意外断开连接时
        override fun onServiceDisconnected(p0: ComponentName?) {
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        //手动解绑服务
        unbindService(conn)
        //界面销毁  关闭cursor
        //获取adapter中的cursor 关闭
        //将adapter中的cursor设置为null
//        adapter?.changeCursor(null)
    }
}