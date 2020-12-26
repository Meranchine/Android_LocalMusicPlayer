package com.example.localmusic.model


import android.database.Cursor
import android.provider.MediaStore

//音乐列表条目bean
data class AudioBean(var data:String, var size:Long, var display_name:String, var artist:String) {
    companion object {

        //根据特定位置上的cursor获取bean
        fun getAudioBean(cursor: Cursor):AudioBean {
            //创建一个audiobean的对象
            val audioBean = AudioBean("",0,"","")
            //判断cursor是否为空
            cursor.let {
                //解析cursor并且设置到bean对象中
                audioBean.data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                audioBean.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                audioBean.display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                audioBean.display_name = audioBean.display_name.substring(0,audioBean.display_name.lastIndexOf("."))
                audioBean.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            }
            return audioBean
        }
    }

}
