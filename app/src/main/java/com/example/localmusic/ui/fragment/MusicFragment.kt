package com.example.localmusic.ui.fragment

import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.example.localmusic.R
import com.example.localmusic.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_music.*
import androidx.recyclerview.widget.LinearLayoutManager as LinearLayoutManager1

class MusicFragment : BaseFragment() {
    override fun initView(): View? {

        return View.inflate(context, R.layout.fragment_music,null)
    }



}