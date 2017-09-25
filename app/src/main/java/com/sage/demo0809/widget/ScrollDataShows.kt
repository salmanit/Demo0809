package com.sage.demo0809.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import com.sage.demo0809.R

/**
 * Created by Sage on 2017/8/24.
 * Description:
 */
class ScrollDataShows : View {
    constructor(context: Context):super(context)
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        var scroler = Scroller(context)
        var viewConfiguration = ViewConfiguration.get(context)

        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollDataShows)

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        typeArray.recycle()
        initSomething(context)
    }



    var lists= arrayListOf<Int>(20,30,40,30,44,22,33,44,22,32,34,22,44,53,64,12,23,23,43)
    fun initSomething(context: Context){

        paintPillar.apply {
            color=Color.parseColor("#44000000")
                    textAlign=Paint.Align.CENTER
        }


        paintBg.apply {
            textSize=60f
            textAlign=Paint.Align.CENTER
            style=Paint.Style.FILL_AND_STROKE
        }
    }
    var paintPillar=Paint(Paint.ANTI_ALIAS_FLAG)
    var paintBg=Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        intervalH=w/8
    }
    var intervalH=100
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        (0..lists.size).map {  }
        paintPillar.color=Color.parseColor("#44000000")
        canvas.drawRect(0f, (height/2).toFloat()-100, width.toFloat(), (height/2+100).toFloat(),paintPillar)
        paintPillar.color=Color.parseColor("#0000ff")
        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(),100f,paintPillar)

        canvas.save()

        canvas.restore()

        val textBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val textCanvas = Canvas(textBitmap)
        paintBg.color=Color.WHITE
        textCanvas.drawText("到底想干啥啊你说一下", (width/2).toFloat(), (height/2).toFloat(),paintBg)
        paintBg.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        paintBg.color=Color.RED
        textCanvas.drawCircle((width/2).toFloat(), (height/2).toFloat(),100f,paintBg)
        paintBg.xfermode=null
        canvas.drawBitmap(textBitmap,0f,0f,null)

    }


}