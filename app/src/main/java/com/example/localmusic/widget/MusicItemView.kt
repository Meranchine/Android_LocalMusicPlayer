package com.example.localmusic.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.localmusic.R
import com.example.localmusic.model.AudioBean
import kotlinx.android.synthetic.main.item_music.view.*

class MusicItemView:RelativeLayout {

    //view和data的绑定
    fun setData(itemBean: AudioBean) {
        //歌曲名称
        title.text=itemBean.display_name
        //歌手名
        artist.text=itemBean.artist
        //歌曲大小
        size.text=Formatter.formatFileSize(context,itemBean.size)

    }

    //new时使用
    constructor(context: Context?) : super(context)
    //布局文件中使用
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    //主题相关使用
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //初始化方法
    init {
        View.inflate(context, R.layout.item_music, this)
    }
}