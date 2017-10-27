package com.sage.demo0809.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import com.sage.demo0809.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_mood_anim.*
import java.util.concurrent.TimeUnit

class ActivityMoodAnim : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_anim)
        views=arrayListOf<View>(iv1,layout1,layout2,layout3,tv_end)
        Observable.interval(200,TimeUnit.MILLISECONDS)
                .take(5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    println("------------------$it")
                    if(views.size>0)
                      startAnim(views.removeAt(0))
                }

        btn_0.setOnClickListener {
            var anim_left=AnimationUtils.loadAnimation(this@ActivityMoodAnim,R.anim.anim_center_left)
            layout_left.startAnimation(anim_left)
            var anim_right=AnimationUtils.loadAnimation(this@ActivityMoodAnim,R.anim.anim_right_center)
            layout_right.startAnimation(anim_right)
            layout_right.visibility=View.VISIBLE
        }

        btn2.setOnClickListener { Toast.makeText(this@ActivityMoodAnim,"click",Toast.LENGTH_SHORT).show() }
    }
    lateinit var views:ArrayList<View>
    fun getAnim() :Animation{
        var anim= AnimationUtils.loadAnimation(this,R.anim.anim_bottom_current)
        anim.interpolator=AccelerateDecelerateInterpolator()

        return anim
    }
    fun startAnim(v:View) {
        var anim= AnimationUtils.loadAnimation(this,R.anim.anim_bottom_current)
        anim.interpolator=OvershootInterpolator()
        anim.fillAfter=true
        v.startAnimation(anim)
        v.visibility=View.VISIBLE
    }

}
