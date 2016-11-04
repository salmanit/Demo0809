package com.sage.demo0809.ui;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sage.demo0809.R;

public class ScrollingActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setContentInsetsAbsolute(0,0);
//        toolbar.setNavigationIcon(R.drawable.lib_btn_back);
        findViewById(R.id.toolbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext(ActivityStep.class,"s健康");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from(findViewById(R.id.scrollView));
                if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                show();
            }
        });
    }
    String[] lists={"1111","2222","3333","1111","2222","3333","1111","2222",
            "3333","1111","2222","3333","1111","2222","3333","1111","2222","3333","1111","2222","3333"
            ,"1111","2222","3333","1111","2222","3333","1111","2222","3333","1111","2222","3333"
            ,"1111","2222","3333","1111","2222","3333","1111","2222","3333","1111","2222","3333"};
    BottomSheetDialog dialog;
    private void  show(){
        View view= LayoutInflater.from(this).inflate(R.layout.bottom_view,null);
        ListView lv= (ListView) view.findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lists));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
            }
        });
         dialog=new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
    }


    private void test(){

    }
}
