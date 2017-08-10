package com.sage.demo0809.custom

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.sage.demo0809.R
import com.sage.demo0809.bean.RvParent

/**
 * Created by Sage on 2017/7/15.
 */
class ItemDecorationFloat:RecyclerView.ItemDecoration() {

    var lists= ArrayList<RvParent>()

    var p=Paint()
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        var manager=parent.layoutManager as LinearLayoutManager
        var first=manager.findFirstVisibleItemPosition()
        if(lists.size==0){
            return
        }
        var moveTop=0

        var rvParent=lists[first]
        var view=LayoutInflater.from(parent.context).inflate(R.layout.include_item_float,null)
        (view.findViewById(R.id.tv_parent) as TextView).text=rvParent.name
        var bp: Bitmap? = getBitmapFromView(view,parent.width) ?: return
        if(first<lists.size-1){
            var nextTop=manager.findViewByPosition(first+1).top
            if(nextTop<bp!!.height){
                moveTop=nextTop-bp.height
            }
        }
       c.drawBitmap(bp,0f,moveTop.toFloat(),p)

    }
    fun getBitmapFromView(view: View,width:Int): Bitmap? {
        view.destroyDrawingCache()
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        println("width==${view.measuredWidth} height====${view.measuredHeight}")
        view.layout(0, 0, width, view.measuredHeight)
        view.setBackgroundColor(Color.parseColor("#f4f4f4"))
        view.isDrawingCacheEnabled = true
        val bitmap = view.getDrawingCache(true)
        return bitmap
    }


}