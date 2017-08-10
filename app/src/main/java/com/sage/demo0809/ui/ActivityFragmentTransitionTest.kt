package com.sage.demo0809.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.sage.demo0809.R
import com.sage.demo0809.fragment.transition.FragmentTransitionHome

class ActivityFragmentTransitionTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_transition_test)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.layout_root_fragment,  FragmentTransitionHome.newInstance())
                    .commit()
        }

    }
}
