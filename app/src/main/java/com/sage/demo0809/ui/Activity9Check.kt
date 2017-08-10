package com.sage.demo0809.ui

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView

import com.sage.demo0809.R
import com.sage.demo0809.widget.lock.LockPatternView
import com.sage.demo0809.widget.lock.LockPatternView2
import kotlinx.android.synthetic.main.activity9_check.*

class Activity9Check : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity9_check)





        tv_set.setOnClickListener {
            state=true
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
    }
    var state=true
    var oldPsw=""
    var nowPsw=""
}
