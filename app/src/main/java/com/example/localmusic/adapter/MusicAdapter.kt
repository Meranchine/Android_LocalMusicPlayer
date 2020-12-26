package com.example.localmusic.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.example.localmusic.model.AudioBean
import com.example.localmusic.widget.MusicItemView


//music界面列表适配器
class MusicAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {
    //创建条目view
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return MusicItemView(context)
    }

    //view+data
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        //view
        val itemView = view as MusicItemView
        //data
        val itemBean=cursor?.let { AudioBean.getAudioBean(it) }
        //view+data
        if (itemBean != null) {
            itemView.setData(itemBean)
        }
    }
}