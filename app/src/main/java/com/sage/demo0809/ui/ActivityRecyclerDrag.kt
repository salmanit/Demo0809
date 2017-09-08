package com.sage.demo0809.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.Toast

import com.sage.demo0809.R
import com.sage.demo0809.adapter.MyRvViewHolder
import com.sage.demo0809.adapter.MySimpleRvAdapter
import com.sage.demo0809.adapter.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_recycler_drag.*
import java.util.*

class ActivityRecyclerDrag : AppCompatActivity() {

    var testData= arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_drag)

        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                rv_linear.visibility=View.VISIBLE
                rv_grid.visibility=View.GONE
            }else {
                rv_linear.visibility=View.GONE
                rv_grid.visibility=View.VISIBLE
            }
        }
        (0..22).map { testData.add("test$it") }
        rv_linear.apply {
            layoutManager=LinearLayoutManager(this@ActivityRecyclerDrag)

            adapter=object :MySimpleRvAdapter<String>(testData){
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item_rv_linear
                }

                override fun handleData(holder: MyRvViewHolder, position: Int, t: String) {
                        holder.setText(R.id.tv_name,t)
                }
            }
            addItemDecoration(object :RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.bottom=20
                }
            })

            ItemTouchHelper(object :ItemTouchHelper.Callback(){
                override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                    return false
                }

                override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                    return ItemTouchHelper.Callback.makeMovementFlags(0
                    ,ItemTouchHelper.START or ItemTouchHelper.END)
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    println("onSwiped=====================$direction")
                    val delete=viewHolder.adapterPosition
                    adapter.notifyItemRemoved(delete)
                    testData.removeAt(delete)
                }
            }).attachToRecyclerView(rv_linear)
        }
        rv_grid.apply {
            var helper=ItemTouchHelper(object :ItemTouchHelper.Callback(){
                //根据返回值来确定是否处理某次拖拽或滑动事件
                override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                    println("getMovementFlags=====================")
                    if(viewHolder.adapterPosition==0){//第一个不允许拖动
                        return 0
                    }
                   return  makeMovementFlags(ItemTouchHelper.DOWN or ItemTouchHelper.UP
                           or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,0)
                }

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                    target: RecyclerView.ViewHolder): Boolean {
                    var from=viewHolder.adapterPosition
                    var to=target.adapterPosition
                    println("onMove=============from=$from  to=$to ======")
                    if(to==0){//第一个不许拖动，自然也不允许拖到到这里
                        return false
                    }
                    if(from<to){
                        for(i in from ..to-1){
                            Collections.swap(testData,i,i+1)
                        }
                    }else{
                        for(i in to ..from-1){
                            Collections.swap(testData,i,i+1)
                        }
                    }
                    adapter.notifyItemMoved(from,to)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    println("onSwiped=====================$direction")
                    val delete=viewHolder.adapterPosition
                    adapter.notifyItemRemoved(delete)
                    testData.removeAt(delete)
                }

                override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder, actionState: Int) {
                    super.onSelectedChanged(viewHolder, actionState)
                    println("onSelectedChanged======================$actionState")
                    if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
                        viewHolder.itemView.setBackgroundColor(Color.LTGRAY)
                    }
                }

                override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                    super.clearView(recyclerView, viewHolder)
                    println("clearView======================")
                    viewHolder.itemView.setBackgroundColor(Color.WHITE)
                }


                override fun isLongPressDragEnabled(): Boolean {
                    println("isLongPressDragEnabled======================")
                    return super.isLongPressDragEnabled()
                }
            })
            helper.attachToRecyclerView(rv_grid)
            layoutManager=GridLayoutManager(this@ActivityRecyclerDrag,4)

            adapter=object :MySimpleRvAdapter<String>(testData){
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item_rv_grid
                }

                override fun handleData(holder: MyRvViewHolder, position: Int, t: String) {
                    holder.setText(R.id.tv_name,t)
                }

            }
            addOnItemTouchListener(object :OnRecyclerItemClickListener(rv_grid){
                override fun onItemClick(vh: RecyclerView.ViewHolder?, position: Int) {
                    Toast.makeText(this@ActivityRecyclerDrag,"click$position",Toast.LENGTH_SHORT).show()
                }
            })
            addItemDecoration(object :RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

                    outRect.bottom=20
                    outRect.right=20
                    var position=parent.getChildAdapterPosition(view)
                    var count=parent.layoutManager.itemCount
                    println("count====$count position===$position")
                    if(position%4==3){
                        outRect.right=0
                    }
                }
                var paint=Paint(Paint.ANTI_ALIAS_FLAG)
                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    var childCount=parent.childCount
                    paint.color= Color.RED
                    println("onDraw=========childCount==$childCount")
                    for(i in 0..childCount-1){
                        try {
                            var child=parent.getChildAt(i)
                            var params=child.layoutParams

                            c.drawRect(child.left.toFloat(),child.bottom.toFloat(),
                                    child.right.toFloat(),(child.bottom+20).toFloat(),paint)
                            if(i%4!=3)
                            c.drawRect(child.right.toFloat(),child.top.toFloat(),
                                    child.right.toFloat()+20,(child.bottom).toFloat()+20,paint)
                        } catch(e: Exception) {
                        }
                    }
                }
            })
        }
    }
}
