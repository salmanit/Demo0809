package com.sage.demo0809.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.mic.etoast2.EToast2
import com.mic.etoast2.Toast
import com.sage.demo0809.R
import kotlinx.android.synthetic.main.activity_outline_test.*

/**
 * Created by Sage on 2017/7/24.
 */
class ActivityOutLineTest : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outline_test)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            tv_test.outlineProvider = ViewOutlineProvider.BACKGROUND

            tv_test2.outlineProvider = ViewOutlineProvider.BOUNDS
            tv_test3.outlineProvider = ViewOutlineProvider.PADDED_BOUNDS


            layout_bottom.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setRect(0, -7, view.width, view.height)

                    }
                }
            }
            layout_bottom.elevation = 3f
            tv_test3.elevation = 3f
            tv_test.elevation = 3f
            tv_test2.elevation = 3f
//            layout_bottom.clipToOutline=true
//            tv_test.clipToOutline=true
//            tv_test2.clipToOutline=true
//            tv_test3.clipToOutline=true
        }


        layout_anim.setOnClickListener {
            animtest()

        }

        tv_test.setOnClickListener {
            animStart()
            Toast.makeText(this, "toast2", EToast2.LENGTH_SHORT).show()
        }
        tv_test2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                animPause()
            }
            android.widget.Toast.makeText(this, "toast", android.widget.Toast.LENGTH_SHORT).show()
        }
        tv_test3.setOnClickListener {
            animNext()
        }
    }

    fun animtest() {
        var set = AnimationSet(true)
        set.fillAfter = false
        set.duration = 400
        var animScale = AnimationUtils.loadAnimation(this, R.anim.scale1tohalf)
        var animTrans = AnimationUtils.loadAnimation(this, R.anim.translate_b2t)
        var alpha1to0 = AlphaAnimation(1f, 0f)
        alpha1to0.duration = 200
        alpha1to0.startOffset = 200
        animTrans.startOffset = 200
        set.addAnimation(animScale)
        set.addAnimation(animTrans)
        set.addAnimation(alpha1to0)
        layout_anim.startAnimation(set)
//        var alpha=AlphaAnimation(0.1f, 1f)
//        alpha.duration=250
//        layout_anim.startAnimation(alpha)
    }

    fun animSetTest() {
        var set = AnimationUtils.loadAnimation(this, R.anim.scale1tohalf)
        layout_anim.startAnimation(set)
    }

    var icon_anim: ObjectAnimator? = null
    fun animStart() {

        initAnim()
        icon_anim!!.start()
    }

    fun animNext() {
        initAnim()
        layout_anim.rotationY = 0f
        icon_anim!!.start()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun animPause() {

        initAnim()
        icon_anim!!.pause()
    }

    fun initAnim() {
        if (icon_anim == null) {
            icon_anim = ObjectAnimator.ofFloat(layout_anim, "rotationX", 0.0f, 359.0f)//
            icon_anim!!.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                override fun onAnimationUpdate(animation: ValueAnimator?) {

                }
            })

            icon_anim!!.duration = 3000
        }
    }
}