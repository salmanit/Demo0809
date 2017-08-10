package com.sage.demo0809.ui

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewOutlineProvider

import com.sage.demo0809.R
import com.sage.demo0809.adapter.MyRvViewHolder
import com.sage.demo0809.adapter.MySimpleRvAdapter
import kotlinx.android.synthetic.main.activity_floating_collspsing.*

class ActivityFloatingCollspsing : AppCompatActivity() {

    var lists = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_collspsing)
        if (Build.VERSION.SDK_INT >= 21)
            appbar.outlineProvider = ViewOutlineProvider.BACKGROUND
        (0..50).mapTo(lists) { "simple$it" }
        rv_test.apply {
            layoutManager = LinearLayoutManager(this@ActivityFloatingCollspsing)
            adapter = object : MySimpleRvAdapter<String>(lists) {
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item
                }

                override fun handleData(holder: MyRvViewHolder?, position: Int, t: String?) {

                }
            }
        }

        tv_click.setOnClickListener {
            val params =appbar.layoutParams as CoordinatorLayout.LayoutParams
            var behavior=params.behavior as AppBarLayout.Behavior
            behavior.onNestedFling(coordinateLayout,appbar,rv_test,0f,rv_test.top.toFloat(),true)
        }
    }
}
