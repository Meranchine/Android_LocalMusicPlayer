package com.example.localmusic.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.SeekBar
import com.example.localmusic.R
import com.example.localmusic.base.BaseActivity
import com.example.localmusic.model.AudioBean
import com.example.localmusic.service.AudioService
import com.example.localmusic.service.Iservice
import com.example.localmusic.util.StringUtil
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import kotlinx.coroutines.NonCancellable.start

class AudioPlayerActivity: BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    var audioBean:AudioBean?=null
    var drawable:AnimationDrawable?=null
    var duration: Int = 0
    val MSG_PROGRESS = 0
    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg?.what) {
                MSG_PROGRESS -> startUpdateProgress()
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.state -> updatePlayState()
//            R.id.mode -> updatePlayMode()
//            R.id.pre->iService?.playPre()
//            R.id.next->iService?.playNext()
//            R.id.playlist->showPlayList()
        }
    }

    /**
     * 接收eventbus方法
     */
    fun onEventMainThread(itemBean: AudioBean) {
//            //设置播放歌曲名称
//            lyricView.setSongName(itemBean.display_name)
        //记录播放歌曲bean
        this.audioBean = itemBean
        //歌曲名
        audio_title.text = itemBean.display_name
        //歌手名
        artist.text = itemBean.artist
        //更新播放状态按钮
        updatePlayStateBtn()
            //动画播放
             drawable = audio_anim.drawable as AnimationDrawable
//            drawable = audio_anim.drawable as AnimationDrawable
            drawable?.start()

            //获取总进度
            duration = iService?.getDuration() ?: 0
            //进度条设置进度最大值
            progress_sk.max = duration
            //更新播放进度
            startUpdateProgress()
//            //设置歌词播放总进度
//            lyricView.setSongDuration(duration)


//            //更新播放模式图标
//            updatePlayModeBtn()
    }
    /**
     * 开始更新进度
     */
    private fun startUpdateProgress() {
        //获取当前进度
        val progress: Int = iService?.getProgress() ?: 0
        //更新进度数据
        updateProgress(progress)
        //定时获取进度
        handler.sendEmptyMessage(MSG_PROGRESS)
    }

    /**
     * 根据当前进度数据更新界面
     */
    private fun updateProgress(pro: Int) {
        //更新进度数值
        progress.text = StringUtil.parseDuration(pro) + "/" + StringUtil.parseDuration(duration)
        //更新进度条
        progress_sk.setProgress(pro)
//        //更新歌词播放进度
//        lyricView.updateProgress(pro)
    }

    /**
     * 更新播放状态
     */

    private fun updatePlayState() {
        //更新播放状态
        iService?.updatePlayState()
        //更新播放状态图标
        updatePlayStateBtn()
    }

     // 根据播放状态更新图标

    private fun updatePlayStateBtn() {
        //获取当前播放状态
        val isPlaying = iService?.isPlaying()
        isPlaying?.let {
            //根据状态更新图标
            if (isPlaying) {
                //播放
                state.setImageResource(R.drawable.selector_btn_audio_play)
                //开始播放动画
                drawable?.start()
                //开始更新进度
                handler.sendEmptyMessage(MSG_PROGRESS)
            } else {
                //暂停
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                //停止播放动画
                drawable?.stop()
                //停止更新进度
                handler.removeMessages(MSG_PROGRESS)
            }
        }
    }

    override fun initListener() {
        //播放状态切换
        state.setOnClickListener(this)
        back.setOnClickListener { finish() }
        //进度条变化监听
        progress_sk.setOnSeekBarChangeListener(this)
//        //播放模式点击事件
//        mode.setOnClickListener(this)
//        //上一曲和下一曲点击事件
//        pre.setOnClickListener(this)
//        next.setOnClickListener(this)


    }
    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    val conn by lazy { AudioConnection() }
    override fun initData() {
        //注册EventBus
        EventBus.getDefault().register(this)
//        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
//        val position = intent.getIntExtra("position",-1)


        //通过audioservice播放音乐
        val intent = intent
        //修改
        intent.setClass(this, AudioService::class.java)

        //通过intent将list以及position传递过去
//        intent.putExtra("list",list)
//        intent.putExtra("position",position)
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
    var iService: Iservice? = null
    inner class AudioConnection : ServiceConnection {

        //service连接时
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            iService = p1 as Iservice
        }

          //意外断开连接时
        override fun onServiceDisconnected(p0: ComponentName?) {
        }


    }


    /**
     * 进度改变回调
     * p1:改变之后的进度
     * p2:true 通过用户手指拖动改变进度  false代表通过代码方式改变进度
     */
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        //判断是否是用户操作
        if (!p2) return
        //更新播放进度
        iService?.seekTo(p1)
        //更新界面进度显示
        updateProgress(p1)
    }

    /**
     * 手指触摸seekbar回调
     */
    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    /**
     * 手指离开seekbar回调
     */
    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        //手动解绑服务
        unbindService(conn)
        //反注册EventBus
        EventBus.getDefault().unregister(this)
        //清空handler发送的所有消息
        handler.removeCallbacksAndMessages(null)
        //界面销毁  关闭cursor
        //获取adapter中的cursor 关闭
        //将adapter中的cursor设置为null
//        adapter?.changeCursor(null)
    }
}