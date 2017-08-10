package com.sage.demo0809.fragment.transition

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sage.demo0809.R

class FragmentTransition2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_transition2, container, false)
    }

    companion object {
        fun newInstance(): FragmentTransition2 {
            val fragment = FragmentTransition2()
            return fragment
        }
    }


}
