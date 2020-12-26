package com.example.localmusic.ui.activity


import android.os.Build
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import com.example.localmusic.R
import com.example.localmusic.base.BaseActivity
import com.example.localmusic.util.FragmentUtil
import com.example.localmusic.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find



class MainActivity : BaseActivity() ,ToolBarManager{
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {
        initMainToolBar()
    }


    override fun initListener() {
        //设置tab切换监听
        bottomBar.setOnTabSelectListener {
//            it代表参数tabId
            val transaction = supportFragmentManager.beginTransaction()
            FragmentUtil.fragmentUtil.getFragment(it)?.let { it1 ->
                transaction.replace(R.id.container,
                    it1,it.toString())
            }

            transaction.commit()
        }
    }

}