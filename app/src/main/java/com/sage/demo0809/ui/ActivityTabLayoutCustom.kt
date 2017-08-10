package com.sage.demo0809.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

import com.sage.demo0809.R
import com.sage.demo0809.fragment.FragmentColor
import kotlinx.android.synthetic.main.activity_tab_layout_custom.*

class ActivityTabLayoutCustom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout_custom)

        vp1.adapter=adapter
        tab1.setupWithViewPager(vp1)

        vp2.adapter=adapter
        tab2.setupWithViewPager(vp2)

    }

    var titles= arrayOf("红色","蓝色","黄色","绿色","灰色")
    var adapter=object :FragmentPagerAdapter(supportFragmentManager){
        override fun getItem(position: Int): Fragment {

            return FragmentColor.instance(position)
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }
    }
}
