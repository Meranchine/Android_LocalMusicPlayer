package com.example.localmusic.ui.fragment



import android.Manifest
import android.app.Activity
import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import android.view.View
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.localmusic.R
import com.example.localmusic.adapter.MusicAdapter
import com.example.localmusic.base.BaseFragment
import com.example.localmusic.util.CursorUtil

import kotlinx.android.synthetic.main.fragment_music.*

class MusicFragment() : BaseFragment(), Parcelable {
    //    val handler = object :Handler() {
//        override fun handleMessage(msg: Message) {
//            msg.let {
//                val cursor:Cursor = msg.obj as Cursor//as强转类型
//                //打印数据
//                CursorUtil.logCursor(cursor)
//            }
//        }
//    }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_music, null)
    }

//    override fun init() {
//        if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),0)
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }

    override fun initData() {

        //加载音乐列表数据
        val resolver = context?.contentResolver
//        val cursor = resolver?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//            arrayOf(MediaStore.Audio.Media.DATA,
//                    MediaStore.Audio.Media.SIZE,
//                    MediaStore.Audio.Media.DISPLAY_NAME,
//                    MediaStore.Audio.Media.ARTIST),
//            null,null,null)
//        //打印所有数据
//        if (cursor != null) {
//            CursorUtil.logCursor(cursor)
//        }

        //开启线程查询音乐数据
//        Thread(object : Runnable {
//            override fun run() {
//                val cursor = resolver?.query(
//                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                    arrayOf(
//                        MediaStore.Audio.Media.DATA,
//                        MediaStore.Audio.Media.SIZE,
//                        MediaStore.Audio.Media.DISPLAY_NAME,
//                        MediaStore.Audio.Media.ARTIST
//                    ),
//                    null, null, null
//                )
//                val msg = Message.obtain()
//                msg.obj = cursor
//                handler.sendMessage(msg)
//            }
//        }).start()
        //asynctask
//        AudioTask().execute(resolver)
        val handler = object : AsyncQueryHandler(resolver) {
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                //查询完成 回调 主线程中
                //打印数据
                CursorUtil.logCursor(cursor)
                //刷新列表
                //设置数据源
                //刷新adapter
                (cookie as MusicAdapter).swapCursor(cursor)
            }
        }

        //开始查询
        handler.startQuery(
            0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST
            ),
            null, null, null
        )
    }

    var adapter: MusicAdapter? = null

    constructor(parcel: Parcel) : this() {

    }

    override fun initListener() {
        adapter = MusicAdapter(context, null)
//        listView.adapter = adapter
    }

    //音乐查询异步任务
//    class AudioTask : AsyncTask<ContentResolver, Void, Cursor>() {
//        //后台执行任务 新线程
//        override fun doInBackground(vararg p0: ContentResolver?): Cursor? {
//            val cursor = p0[0]?.query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                arrayOf(
//                    MediaStore.Audio.Media.DATA,
//                    MediaStore.Audio.Media.SIZE,
//                    MediaStore.Audio.Media.DISPLAY_NAME,
//                    MediaStore.Audio.Media.ARTIST
//                ),
//                null, null, null
//            )
//            return cursor
//        }
//
//        //将后台任务结果回调到主线程中
//        override fun onPostExecute(result: Cursor?) {
//            super.onPostExecute(result)
//            //打印cursor
//            CursorUtil.logCursor(result)
//        }
//
//    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicFragment> {
        override fun createFromParcel(parcel: Parcel): MusicFragment {
            return MusicFragment(parcel)
        }

        override fun newArray(size: Int): Array<MusicFragment?> {
            return arrayOfNulls(size)
        }
    }
}