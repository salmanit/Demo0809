package com.sage.demo0809.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchUIUtil
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.sage.demo0809.R
import com.sage.demo0809.adapter.MyRvViewHolder
import com.sage.demo0809.adapter.MySimpleRvAdapter
import com.sage.demo0809.adapter.SpaceItemDecoration
import com.sage.demo0809.bean.RvChild
import com.sage.demo0809.bean.RvParent
import com.sage.demo0809.custom.ItemDecorationFloat
import kotlinx.android.synthetic.main.activity_recycler_view_handle.*
import rx.Observer
import java.util.*

class ActivityRecyclerViewHandle : AppCompatActivity() {

     var lists= ArrayList<RvParent>()
    var currentItem = 0
    lateinit var itemDecorationFloat:ItemDecorationFloat
    lateinit var helper:ItemTouchHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_handle)

        rv_left.apply {
            layoutManager = LinearLayoutManager(this@ActivityRecyclerViewHandle)
            addItemDecoration(SpaceItemDecoration(1))
            adapter = object : MySimpleRvAdapter<RvParent>() {
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item_rv_handle_parent
                }

                override fun handleData(holder: MyRvViewHolder, position: Int, data: RvParent) {
                    holder.setText(R.id.tv_parent_name, data.name)
                    var colorTXT = Color.parseColor("#333333")
                    var colorBG = Color.parseColor("#ffffff")
                    if (position == currentItem) {
                        colorTXT = Color.parseColor("#0068cf")
                        colorBG = Color.parseColor("#f3f3f3")
                    }
                    holder.getView<ImageView>(R.id.iv_parent_logo).setColorFilter(colorTXT)
                    holder.getView<TextView>(R.id.tv_parent_name).setTextColor(colorTXT)
                    holder.getmConvertView().setBackgroundColor(colorBG)
                    holder.getmConvertView().setOnClickListener {
                        currentItem=position
                        notifyDataSetChanged()
                        var manager=rv_right.layoutManager as LinearLayoutManager
                        manager.scrollToPositionWithOffset(position,0)
                    }
                }
            }
        }


        rv_right.apply {
            layoutManager = LinearLayoutManager(this@ActivityRecyclerViewHandle)
            itemDecorationFloat=ItemDecorationFloat()
            addItemDecoration(itemDecorationFloat)
            adapter = object : MySimpleRvAdapter<RvParent>() {
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item_rv_handle_child
                }

                override fun handleData(holder: MyRvViewHolder, position: Int, rvParent: RvParent) {
                    holder.setText(R.id.tv_parent, rvParent.name)
                    holder.getmConvertView().setOnClickListener {
                        Toast.makeText(context,"${rvParent.name}",Toast.LENGTH_SHORT).show()
                    }
                    holder.getView<RecyclerView>(R.id.rv_child_item).apply {
                        layoutManager = GridLayoutManager(this@ActivityRecyclerViewHandle,3)
                        addItemDecoration(SpaceItemDecoration(1))
                        adapter = object : MySimpleRvAdapter<RvChild>(rvParent.child) {
                            override fun layoutId(viewType: Int): Int {
                                return R.layout.item_rv_handle_child_child
                            }

                            override fun handleData(holder: MyRvViewHolder, position: Int, rvChild: RvChild) {
                                holder.setText(R.id.tv_child2_name, rvChild.name)

                            }
                        }
                        helper=ItemTouchHelper(object :ItemTouchHelper.Callback(){
                            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                                return  makeMovementFlags(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN
                                        ,0)
                            }

                            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                                var old=viewHolder.adapterPosition;
                                var now=target.adapterPosition;
                                if(old<now){
                                    (old..now-1).forEach { Collections.swap(rvParent.child,it,it+1) }
                                }else{
                                    (now..old-1).forEach { Collections.swap(rvParent.child,it,it+1) }
                                }
                                println("from=$old= to=$now===count=${rvParent.child.size}=======")
                                adapter.notifyItemMoved(old,now)
                                return true
                            }

                            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder, actionState: Int) {
                                println("onSelectedChanged================$actionState")
                                if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){
                                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY)
                                }
                            }

                            override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
                                    println("clearView====================")
                                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
                            }
                            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                                println("==========onSwiped==$direction")
                            }
                        })
                        helper.attachToRecyclerView(this)
                    }

                }
            }
        }

        rv_right.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                var manager=rv_right.layoutManager as LinearLayoutManager
                var first=manager.findFirstVisibleItemPosition()
                var last=manager.findLastCompletelyVisibleItemPosition()
                println("onScrolled============$first====$last===$currentItem===")
                if(first!=currentItem){
                    currentItem=first
                    rv_left.scrollToPosition(currentItem)
                    rv_left.adapter.notifyDataSetChanged()
                }else{
                    if(last==lists.size-1&&last!=currentItem){
                        currentItem=last
                        rv_left.scrollToPosition(currentItem)
                        rv_left.adapter.notifyDataSetChanged()
                    }
                }
                handleFloat()
            }

        })
        getData()

        (rv_left.adapter as MySimpleRvAdapter<RvParent>).setLists(lists)
        (rv_right.adapter as MySimpleRvAdapter<RvParent>).setLists(lists)
        itemDecorationFloat.lists=lists
        layout_float.visibility=View.INVISIBLE
    }

    var last=-1
    var lastMoveTop=0;
    /**这种悬浮窗的目的是为了处理点击事件。画出来的ItemDecorationFloat不好处理点击事件*/
    fun handleFloat(){
        if(lists.size==0){
            return
        }
        var manager=rv_right.layoutManager as LinearLayoutManager
        var first=manager.findFirstVisibleItemPosition()
        var moveTop=0
        if(last!=first){
            var rvParent=lists[first]
            tv_parent_float.text=rvParent.name
            last=first
        }
        if(first<lists.size-1){
            var nextTop=manager.findViewByPosition(first+1).top
            if(nextTop<layout_float.height){
                moveTop=nextTop-layout_float.height
            }
        }
        println("moveTop======$moveTop")
        if(lastMoveTop!=moveTop){
            layout_float.translationY=moveTop.toFloat()
            lastMoveTop=moveTop
        }

    }

    private fun getData() {
        (0..10).mapTo(lists) {
            var childs = ArrayList<RvChild>()
            var child=8;
            if(it==10){
                child=20
            }
            (0..child).mapTo(childs) { RvChild("", "child$it", it) }
            RvParent("", "parent$it", childs)
        }
        println("size=============${lists.size}")
//        for( i in 0..10){
//            var childs=ArrayList<RvChild>()
//            (0..11).mapTo(childs) { RvChild("","child$it", it) }
//            lists.add(RvParent("","parent$i",childs))
//        }
    }
}
