package com.example.localmusic.util

import android.database.Cursor

//cursor打印util

object CursorUtil {
    fun logCursor(cursor: Cursor?){
        cursor?.let {
            //将cursor游标复位
            it.moveToPosition(-1)
            while (it.moveToNext()){
                for (index in 0 until it.columnCount){
                    println("key=${it.getColumnName(index)} --value=${it.getString(index)}")

                }
            }
        }
    }

}