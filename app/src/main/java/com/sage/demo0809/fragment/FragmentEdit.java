package com.sage.demo0809.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;

/**
 * Created by Sage on 2016/9/30.
 */

public class FragmentEdit extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit,container,false);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(getView()!=null){
            Toast.makeText(getActivity(),""+menuVisible,Toast.LENGTH_SHORT).show();
            MyLog.i(menuVisible+"========="+isInputOpen(getActivity()));
            if(menuVisible){
                if(!isInputOpen(getActivity())){
                    showInput(getActivity(),getView().findViewById(R.id.editText));
                }
            }else{
                hiddenInput(getActivity());
            }

        }else{
            Toast.makeText(getActivity(),"null="+menuVisible,Toast.LENGTH_SHORT).show();
            MyLog.i(menuVisible+"===null======"+isInputOpen(getActivity()));
        }
    }

    public boolean isInputOpen(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public void showInput(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public void hiddenInput(Activity context){
        ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
