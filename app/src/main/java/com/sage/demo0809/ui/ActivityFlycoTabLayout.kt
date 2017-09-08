package com.sage.demo0809.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.sage.demo0809.R
import kotlinx.android.synthetic.main.activity_flyco_tab_layout.*

class ActivityFlycoTabLayout : AppCompatActivity() {
    private val mTitles_2 = arrayOf("首页", "消息", "联系人")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flyco_tab_layout)

        tl_4.setTabData(mTitles_2)

        btn_result.setOnClickListener {
            btn_result.text = "choose${tl_4.currentTab}"
        }
    }
}
