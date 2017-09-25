package com.sage.demo0809.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar

import com.sage.demo0809.R
import kotlinx.android.synthetic.main.activity_flyco_tab_layout.*
import com.sage.demo0809.widget.SaleProgressView



class ActivityFlycoTabLayout : AppCompatActivity() {
    private val mTitles_2 = arrayOf("首页", "消息", "联系人")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flyco_tab_layout)

        tl_4.setTabData(mTitles_2)

        val saleProgressView = findViewById(R.id.spv) as SaleProgressView
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
           override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                saleProgressView.setTotalAndCurrentCount(100, i)
               sds2.updateProgress(i*1f)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

    }
}
