package com.example.localmusic.util

import android.database.Cursor

//cursor打印Util类
object CursorUtil {
    fun logCursor(cursor: Cursor?) {
        cursor?.let {
            //将cursor游标复位
            it.moveToPosition(-1)
            while (it.moveToNext()) {
                for (index in 0 until it.columnCount) {//columnCount当前有多少个字段 until不包含
                    println("key=${it.getColumnName(index)} --value=${it.getString(index)}")
                }
            }
        }
    }
}