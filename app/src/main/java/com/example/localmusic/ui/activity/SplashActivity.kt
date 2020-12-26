package com.example.localmusic.ui.activity

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.example.localmusic.R
import com.example.localmusic.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: BaseActivity(), ViewPropertyAnimatorListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        ViewCompat.animate(imageView).scaleX(1.0f).scaleY(1.0f).setListener(this).setDuration(2000)
    }

    override fun onAnimationStart(view: View?) {

    }

    override fun onAnimationEnd(view: View?) {
        //进入主界面
//        startActivity<MainActivity>()
//        finish()
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(view: View?) {

    }
}