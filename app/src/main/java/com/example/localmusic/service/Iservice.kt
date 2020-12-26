package com.example.localmusic.service

interface Iservice {
    fun updatePlayState()
    fun isPlaying():Boolean?
    abstract fun getDuration(): Int?
    abstract fun getProgress(): Int?
    abstract fun seekTo(p1: Int)

}