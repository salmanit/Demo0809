package com.sage.demo0809.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker

import com.sage.demo0809.R
import kotlinx.android.synthetic.main.activity_chips_layout.*
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration
import com.google.android.flexbox.*


class ActivityLondonEyeLayout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_london_eys_layout)
        var manager=ChipsLayoutManager.newBuilder(this)
                .setChildGravity(Gravity.CENTER)
                .setScrollingEnabled(true)
                .setMaxViewsInRow(3)
//                .setGravityResolver(object :IChildGravityResolver {
//                    override fun getItemGravity(p0: Int): Int {
//                        return Gravity.CENTER
//                    }
//                })
                //you are able to break row due to your conditions. Row breaker should return true for that views
//                .setRowBreaker(object :IRowBreaker {
//                    override fun isItemBreakRow(position:Int):Boolean {
//                        return position == 4
//                    }
//                })
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                // whether strategy is applied to last row. FALSE by default
                .withLastRow(true)
                .build()

        var data= arrayListOf("我都说","的了说的","受得","送了水电我","我收代理费",
                "了我我"
//                ,"受得了分水岭的副教授的立方","拉近了看水电费","了我我收水电费爽肤水冯绍峰是"
        )
        var adapterRv=object :RecyclerView.Adapter<SimpleViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
                var view=layoutInflater.inflate(R.layout.item_fm_search_tag,parent,false)
                return SimpleViewHolder(view)
            }

            override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
                holder.setTextView(R.id.tv_tag_name,data[position])
            }

            override fun getItemCount(): Int {
                return data.size
            }

        }
        rv.apply {
            addItemDecoration(SpacingItemDecoration(10,10))
            layoutManager=manager
            adapter=adapterRv

        }



        /**FlexboxLayoutManager的使用*/
        var data2= arrayListOf("我都说的了说的咯","受得了都删了","送了水电我","我收代理费","受得了分水岭的副教授的立方",
                "了我我","拉近了看水电费","了我我收水电费爽肤水冯绍峰是")
        var adapter2=object :RecyclerView.Adapter<SimpleViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
                var view=layoutInflater.inflate(R.layout.item_fm_search_tag,parent,false)
                return SimpleViewHolder(view)
            }

            override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
                val lp = holder.itemView.layoutParams
                if (lp is FlexboxLayoutManager.LayoutParams) {
                    lp.flexGrow = 1f
                    println("not...........................")
                }
                holder.setTextView(R.id.tv_tag_name,data2[position])
            }

            override fun getItemCount(): Int {
                return data2.size
            }
        }
        var flexboxLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
            justifyContent=JustifyContent.CENTER

        }
//        flexboxLayoutManager.alignContent=AlignContent.CENTER//这个不适合manager，加了就挂了

        rv2.apply {
            addItemDecoration(SpacingItemDecoration(10,10))
            layoutManager = flexboxLayoutManager
            adapter = adapter2
        }

    }

    class SimpleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun <T :View>getView(id:Int):T{
            return itemView.findViewById(id) as T
        }
        fun setTextView(id:Int,content:String){
            (itemView.findViewById(id) as TextView).text=content
        }
    }
}
