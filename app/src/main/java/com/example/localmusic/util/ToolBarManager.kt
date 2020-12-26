package com.example.localmusic.util

import android.os.Build
import android.widget.Toolbar
import androidx.annotation.RequiresApi

//所有界面toolbar
interface ToolBarManager {

    val toolbar: Toolbar
    //初始化主界面中的toolbar
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initMainToolBar() {
        toolbar.setTitle("MyPlayer")
    }
}