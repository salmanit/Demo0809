package com.sage.demo0809.ui

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView

import com.sage.demo0809.R
import com.sage.demo0809.fragment.FragmentEdit
import com.sage.demo0809.widget.lock.LockPatternView
import com.sage.demo0809.widget.lock.LockPatternView2
import kotlinx.android.synthetic.main.activity9_check.*

class Activity9Check : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity9_check)

        psw_txt.setInputCallBack {
            showToast(it)
        }
        psw_txt2.setInputCallBack {
            showToast(it)
        }



        tv_set.setOnClickListener {
            state=true
            supportFragmentManager.beginTransaction().add(R.id.layout_root,FragmentEdit(),"edit")
                    .addToBackStack("aa").commit()
        }
        tv_check.setOnClickListener {
            state=false
        }


        lock_view.setLockListener(object :LockPatternView.OnLockListener{
            override fun getStringPassword(password: String) {
                println("getStringPassword===============$password")
                nowPsw=password
            }

            override fun isPassword(): Boolean {
                println("isPassword===================")
                return state||TextUtils.equals(oldPsw,nowPsw)
            }
        })
        lock_view2.setLockListener(object : LockPatternView2.OnLockListener{
            override fun getStringPassword(password: String) {
                println("getStringPassword===============$password")
                nowPsw=password
            }

            override fun isPassword(): Boolean {
                println("isPassword===================")
                return state||TextUtils.equals(oldPsw,nowPsw)
            }
        })



        wv.settings.javaScriptEnabled=true
//        wv.loadUrl("http://www.baidu.com")
        wv.loadUrl("http://readymade1.srapi.cn//Api//Waptv//tv_detail?source=chart&user_id=7085&tv_id=13")
    }
    var state=true
    var oldPsw=""
    var nowPsw=""
}
