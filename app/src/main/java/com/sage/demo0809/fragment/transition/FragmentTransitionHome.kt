package com.sage.demo0809.fragment.transition

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.AutoTransition
import android.transition.Fade
import android.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sage.demo0809.R
import kotlinx.android.synthetic.main.fragment_fragment_transition1.*
import kotlinx.android.synthetic.main.fragment_transition2.*

/**
 * Created by Sage on 2017/7/21.
 */
class FragmentTransitionHome : Fragment() {


    companion object {
        fun newInstance(): FragmentTransitionHome {
            var fragment = FragmentTransitionHome()

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_transition_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewCompat.setTransitionName(iv_share1, "shared....")
        ViewCompat.setTransitionName(iv_share2, "shared....2")
        this@FragmentTransitionHome.exitTransition = Fade()
        iv_share1.setOnClickListener {
            var fragment1 = FragmentTransition1.newInstance()

            fragment1.apply {
                if (Build.VERSION.SDK_INT >= 21) {
                    fragment1.sharedElementEnterTransition = SharedTransitionSet()
                    fragment1.sharedElementReturnTransition = SharedTransitionSet()
                    fragment1.enterTransition = Fade()
                }
            }
//            exitTransition = Fade()
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .addSharedElement(iv_share2, "share1")
                    .addToBackStack(null)
                    .replace(R.id.layout_root_fragment, fragment1, "fragment1")
                    .commit()
                }

        iv_share2.setOnClickListener {
            var fragment2 = FragmentTransition2.newInstance()
            fragment2.apply {
                if (Build.VERSION.SDK_INT >= 21) {
                    sharedElementEnterTransition = AutoTransition()
                    sharedElementReturnTransition = AutoTransition()
                    enterTransition = Fade()
                }
            }

            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .addSharedElement(iv_share1, "share2")
                    .replace(R.id.layout_root_fragment, fragment2, "fragment2")
                    .addToBackStack(null)
                    .commit()
        }
    }

}