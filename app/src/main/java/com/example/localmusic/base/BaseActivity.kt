package com.example.localmusic.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

//所有activity的基类
abstract class BaseActivity:AppCompatActivity(), AnkoLogger{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initData()
    }

    //初始化数据
    open protected fun initData() {

    }

    //adapter listener
    open protected fun initListener() {

    }

    //获取布局id
    abstract fun getLayoutId(): Int

    //
    protected fun myToast(msg: String) {
        runOnUiThread { toast(msg) }
    }

    //开启一个activity并且finish当前界面
    // reified 找到具体的 T 的类型
    inline fun <reified T:BaseActivity> startActivityAndFinish() {
        startActivity<T>()
        finish()
    }
}