package com.sage.demo0809.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import com.sage.demo0809.R

/**
 * Created by Sage on 2017/8/24.
 * Description:
 */
class ScrollDataShows @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        var scroler = Scroller(context)
        var viewConfiguration = ViewConfiguration.get(context)

        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollDataShows)


        typeArray.recycle()
        initSomething(context)
    }
    var lists= arrayListOf<Int>(20,30,40,30,44,22,33,44,22,32,34,22,44,53,64,12,23,23,43)
    fun initSomething(context: Context){
        var paintPillar=Paint()
        paintPillar.color=Color.RED
        paintPillar.strokeWidth=20f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        intervalH=w/8
    }
    var intervalH=100
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        (0..lists.size).map {  }
    }
}