package com.example.localmusic.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.localmusic.widget.MusicListItemView
import org.jetbrains.anko.matchParent

class MusicListAdapter: RecyclerView.Adapter<MusicListAdapter.MusicListHolder>() {
    class MusicListHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListHolder {
        return MusicListHolder(MusicListItemView(parent?.context))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MusicListHolder, position: Int) {

    }

}