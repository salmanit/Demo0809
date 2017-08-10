package com.sage.demo0809.fragment.transition

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sage.demo0809.R

class FragmentTransition1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_fragment_transition1, container, false)
    }

    companion object {

        fun newInstance(): FragmentTransition1 {
            val fragment = FragmentTransition1()
            return fragment
        }
    }
}
