package com.sage.demo0809.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sage.demo0809.R;

/**
 * Created by Sage on 2016/8/10.
 */
public class FragmentDemo extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_demo,container,false);
    }

    FragmentEdit fragmentEdit1;
    FragmentEdit fragmentEdit2;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.iv_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentEdit1==null){
                    fragmentEdit1=new FragmentEdit();
                }
                fragmentEdit1.show(getChildFragmentManager(),"ppppp");
            }
        });
        getView().findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentEdit2==null){
                    fragmentEdit2=new FragmentEdit();
                }
                fragmentEdit2.show(getActivity().getSupportFragmentManager(),"aaaaaa");
            }
        });
    }
}
