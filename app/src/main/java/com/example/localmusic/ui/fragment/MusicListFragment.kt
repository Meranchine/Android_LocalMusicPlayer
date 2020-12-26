package com.example.localmusic.ui.fragment

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.localmusic.R
import com.example.localmusic.adapter.MusicListAdapter
import com.example.localmusic.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_music.*

class MusicListFragment:BaseFragment() {

    override fun initView(): View? {

        return View.inflate(context, R.layout.fragment_music, null)
    }
    override fun initListener() {
        //初始化recycleView
        recycleView.layoutManager = LinearLayoutManager(context)
        //适配
        val adapter = MusicListAdapter()
        recycleView.adapter = adapter
    }

    override fun initData() {
        //加载音乐列表数据

    }

}