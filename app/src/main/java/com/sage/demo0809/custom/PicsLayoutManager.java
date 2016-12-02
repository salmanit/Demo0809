package com.sage.demo0809.custom;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Sage on 2016/12/2.
 */

public class PicsLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(getItemCount()==0||state.isPreLayout()){
            return;
        }
        if(getItemCount()==1){
            addView(recycler.getViewForPosition(0));
        }else if( getItemCount()==2){

        }
    }
}
