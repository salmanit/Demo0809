package com.sage.demo0809.fragment

import android.graphics.Color
import android.os.Bundle
import com.sage.demo0809.R
import kotlinx.android.synthetic.main.fragment_color.*

/**
 * Created by Sage on 2017/8/1.
 */
class FragmentColor:FragmentBase(){
    companion object {
            fun instance(int: Int):FragmentColor{
                var fragment=FragmentColor()
                var bundle=Bundle()
                bundle.putInt("index",int)
                fragment.arguments=bundle
                return fragment

            }
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_color
    }
    var index=0
    var colors= arrayOf(Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.DKGRAY)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(arguments!=null)
            index=arguments.getInt("index")
        tv_.setBackgroundColor(colors[index%colors.size])
        tv_.text = "$index"
    }
}