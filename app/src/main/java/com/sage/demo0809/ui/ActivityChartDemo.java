package com.sage.demo0809.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.bean.SimpleUser;
import com.sage.demo0809.fragment.FragmentDemo;
import com.sage.demo0809.step.ServiceTick;
import com.sage.demo0809.widget.TextViewReply;
import com.zhy.changeskin.SkinManager;
import com.zhy.changeskin.base.BaseSkinActivity;
import com.zhy.changeskin.callback.ISkinChangingCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Sage on 2016/9/10.
 */
public class ActivityChartDemo extends BaseSkinActivity implements SwipeRefreshLayout.OnRefreshListener{

//    SwipeRefreshLayout srf;
    ColumnChartView chart1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_demo);
        ShortcutBadger.applyCount(this, 10);
        startService(new Intent(this, ServiceTick.class));
        TextViewReply textViewReply= (TextViewReply) findViewById(R.id.tv_reply);
        textViewReply.setReplyClickListener(new TextViewReply.TextViewReplyClickListener() {
            @Override
            public void spanClick(SimpleUser simpleUser) {
                    Toast.makeText(ActivityChartDemo.this,simpleUser.userName,Toast.LENGTH_SHORT).show();
            }
        });
        SimpleUser from=new SimpleUser("张三");
        SimpleUser to=new SimpleUser("李四码子");
        textViewReply.setContent(from,to,"just for testjust for testjust for testjust for testjust for testjust for test");
//        srf= (SwipeRefreshLayout) findViewById(R.id.srf);
//        srf.setOnRefreshListener(this);
        chart1= (ColumnChartView) findViewById(R.id.chart1);
        initChart1();
        ((TextView)findViewById(R.id.tv_shade)).setShadowLayer(10,5,5,Color.parseColor("#ff00ff"));
        findViewById(R.id.iv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSkin();
            }
        });
        SkinManager.getInstance().removeAnySkin();
//        setNewDrawable((RadioButton) findViewById(R.id.rb1));

        RadioGroup rgHome= (RadioGroup) findViewById(R.id.rg_home);
        ImageView iv_circle= (ImageView) findViewById(R.id.iv_circle);
        ImageView iv_line= (ImageView) findViewById(R.id.iv_line);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setContentInsetsAbsolute(0,0);
        toolbar.setNavigationIcon(R.drawable.lib_btn_back);
        findViewById(R.id.toolbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            goNext(ScrollingActivity2.class);
            }
        });
        ViewPager vp= (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new FragmentDemo();
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ShortcutBadger.applyCount(ActivityChartDemo.this, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void goNext(Class cla){
        startActivity(new Intent(this,cla));
    }
    private void setNewDrawable(RadioButton view){
        try {
            Drawable drawable = ContextCompat.getDrawable(this,R.drawable.bangdan_sb_img);
            Drawable drawable2 = ContextCompat.getDrawable(this,R.drawable.bangdan_vs_img);
            int[][] states = new int[2][];
            states[0] = new int[] { android.R.attr.state_checked};
            states[1] = new int[] {};
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(states[0],drawable2);//注意顺序
            stateListDrawable.addState(states[1],drawable);
            stateListDrawable.setBounds(0, 6, 180, 180 + 6);
            view.setCompoundDrawables(null,stateListDrawable,null,null);
        } catch (Exception e) {

        }
    }



    private void changeSkin() {
        File file=new File(Environment.getExternalStorageDirectory(),"plugin.apk");
        SkinManager.getInstance().changeSkin(
                file.getAbsolutePath(),
                "sage.plungin",
                new ISkinChangingCallback()
                {
                    @Override
                    public void onStart()
                    {
                        Toast.makeText(ActivityChartDemo.this, "start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e)
                    {
                        Toast.makeText(ActivityChartDemo.this, "换肤失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete()
                    {
                        Drawable drawable = ContextCompat.getDrawable(ActivityChartDemo.this,R.drawable.skin_status_test);
                        Toast.makeText(ActivityChartDemo.this, "换肤成功", Toast.LENGTH_SHORT).show();
                        RadioButton rb2= (RadioButton) findViewById(R.id.rb2);
                        rb2.setCompoundDrawablesWithIntrinsicBounds(null, drawable,null,null);
                    }
                });
    }

    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec",};
    private ColumnChartData columnData;
    public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};
    private void initChart1(){


        int numSubcolumns = 1;
        int numColumns = months.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            axisValues.add(new AxisValue(i).setLabel(months[i]));

            columns.add(new Column(values).setHasLabels(true));
        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2).setHasTiltedLabels(true));
        columnData.setValueLabelBackgroundColor(Color.parseColor("#ffffff"));
        columnData.setValueLabelBackgroundEnabled(false);
        columnData.setValueLabelsTextColor(Color.parseColor("#000000"));
        chart1.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
//        chart1.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        chart1.setValueSelectionEnabled(false);
        chart1.setZoomEnabled(false);
        chart1.setScrollEnabled(true);
        chart1.setZoomType(ZoomType.HORIZONTAL);
        Viewport maxViewport=chart1.getMaximumViewport();
        MyLog.i("44="+maxViewport.toString());
        maxViewport.top+=20;
        MyLog.i("222222="+maxViewport.toString());
        chart1.setMaximumViewport(maxViewport);
        MyLog.i("3333=="+chart1.getMaximumViewport().toString());
    }


    @Override
    public void onRefresh() {
//        srf.setRefreshing(false);
    }
}
