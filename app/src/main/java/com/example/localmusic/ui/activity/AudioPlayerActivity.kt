package com.example.localmusic.ui.activity

import com.example.localmusic.R
import com.example.localmusic.base.BaseActivity
import com.example.localmusic.model.AudioBean

class AudioPlayerActivity: BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    override fun initData() {
        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
        val position = intent.getIntExtra("position",-1)
        //播放音乐
        
    }
}