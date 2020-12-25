package com.example.localmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.localmusic.R

class MusicListItemView :RelativeLayout {
    //new时使用
    constructor(context: Context?) : super(context)
    //布局文件中使用
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    //主题相关使用
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //初始化方法
    init {
        View.inflate(context, R.layout.item_musiclist, this)
    }

}