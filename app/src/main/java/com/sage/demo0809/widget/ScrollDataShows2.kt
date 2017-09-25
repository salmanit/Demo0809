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
class ScrollDataShows2 : View {
    constructor(context: Context):super(context)
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        var scroler = Scroller(context)
        var viewConfiguration = ViewConfiguration.get(context)

        var typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollDataShows)

        typeArray.recycle()
        initSomething(context)
    }



    fun initSomething(context: Context){

        paintPillar.apply {
            color=Color.RED
            style=Paint.Style.STROKE
            strokeWidth=4f
        }


        paintBg.apply {
            textSize=60f
            color=Color.RED
            style=Paint.Style.FILL
        }
        paintTxt.apply {
            textSize=44f
            style=Paint.Style.FILL_AND_STROKE
        }
    }
    var paintPillar=Paint(Paint.ANTI_ALIAS_FLAG)
    var paintBg=Paint(Paint.ANTI_ALIAS_FLAG)
    var paintTxt=Paint(Paint.ANTI_ALIAS_FLAG)
    var angle=0.toDouble()
    var angleRotate=30f//旋转角度
    var c_c=0.toDouble()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
         halfW=width/2-stroke
         halfH=height/2-stroke
        if (baseLineY == 0.0f) {
            val fm = paintTxt.fontMetricsInt
            baseLineY = (height / 2 - (fm.descent / 2 + fm.ascent / 2)).toFloat()
        }

        angle=Math.toDegrees(Math.atan(halfH.toDouble()/halfW.toDouble()))
        c_c=Math.sqrt((halfW*halfW+halfH*halfH).toDouble())
        println("angle===========$angle   c==$c_c")
    }

    internal var baseLineY: Float = 0.toFloat()
    var stroke=2f;//画笔的宽
    var percent=0f//百分比
    var halfW=0f
    var halfH=0f

    fun updateProgress(p: Float){
        percent=p/100
        postInvalidate()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(width/2f,height/2f)

        var rect=RectF(-halfW,-halfH,halfW,halfH)
        canvas.drawRoundRect(rect,halfH, halfH,paintPillar)

        //画斜线
        drawLines(canvas)
        //画进度
        drawProgress(canvas)
        //画文字提示
        drawTextShow(canvas)

        canvas.restore()

    }

    fun drawLines(canvas: Canvas){
        //画间隔线
        var btp=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_4444)
        var c=Canvas(btp)

        c.save()
        var p=Path()
        var rectF=RectF(stroke,stroke,width-stroke,height-stroke)
        p.addRoundRect(rectF,halfH,halfH,Path.Direction.CW)
        c.clipPath(p)
        c.rotate(angleRotate,width/2f,height/2f)//以正中心为轴逆时针旋转30读，

        paintBg.color=Color.parseColor("#22ff0000")
        var x=-100f//旋转30度以后最大最小的x和y的值肯定变了。角度看着头疼，就不算了，加个距离保证比我们画布大就ok拉
        while (x<width+200){
            c.drawRect(x,-300f,x+30,height.toFloat()+300,paintBg)
            x+=40
        }
       var h2= Math.sin(Math.toRadians(angle+angleRotate))*c_c
        var h3=Math.cos(Math.toRadians(angle-angleRotate))*c_c
        println("c_c====$c_c  halfH==$halfH  halfW===$halfW  h2====$h2  h3=====$h3")

        c.restore()
        canvas.drawBitmap(btp,-width/2f,-height/2f,null)
    }
    fun drawProgress(canvas: Canvas){
        paintBg.color=Color.parseColor("#aaff0000")
        var p_w=halfW*2*percent

        canvas.save()
        var rect=RectF(-halfW,-halfH,-halfW+halfW*2*percent,halfH)
        if(p_w<halfH*2){
            var move=halfH*2-p_w
            var p=Path()
            p.addCircle(-halfW+halfH,0f,halfH,Path.Direction.CW)
            canvas.clipPath(p)
            rect=RectF(-halfW-move,-halfH,-halfW+halfH*2-move,halfH)
        }



        canvas.drawRoundRect(rect,halfH, halfH,paintBg)

        canvas.restore()
    }

    fun drawTextShow(canvas: Canvas){
        var btp=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_4444)
        var c=Canvas(btp)

        paintBg.color=Color.RED
        var p_w=(width-stroke*2)*percent
        var r=halfH
        if(p_w<halfH*2){
            r=p_w/2
        }
        var rect=RectF(stroke,height/2-r,stroke+p_w,height/2+r)


        paintTxt.color=Color.RED
        paintTxt.xfermode=null
        if(percent>=1){
            paintTxt.textAlign=Paint.Align.CENTER
            c.drawText("已售罄",width/2f,baseLineY,paintTxt)
        }else{

            if(percent>=0.8){
                paintTxt.textAlign=Paint.Align.CENTER
                c.drawText("即将售罄",width/2f,baseLineY,paintTxt)
            }else{
                paintTxt.textAlign=Paint.Align.LEFT
                c.drawText("已售${(100*percent).toInt()}件",halfH/2,baseLineY,paintTxt)
            }
            paintTxt.textAlign=Paint.Align.RIGHT
            c.drawText("${(percent*100).toInt()}%",width-halfH/2,baseLineY,paintTxt)
        }





        paintTxt.xfermode=PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        paintTxt.color=Color.WHITE


        c.drawRoundRect(rect,r, r,paintTxt)
        canvas.drawBitmap(btp,-width/2f,-height/2f,null)
    }
}