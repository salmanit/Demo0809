package com.sage.demo0809.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import java.util.Random;
import java.util.TimerTask;

import lecho.lib.hellocharts.animation.PieChartRotationAnimator;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Sage on 2016/9/10.
 */
public class ActivityChartDemo extends BaseSkinActivity implements SwipeRefreshLayout.OnRefreshListener{

//    SwipeRefreshLayout srf;
    PieChartView chart1;
    private PieChartData data;


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
        setSupportActionBar(toolbar);
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
        initChartData();
        initColumnChar();
        findViewById(R.id.tv_shade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initChart1();
                initChart2();
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


        int numValues = new Random().nextInt(7)+1;

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 3 , ChartUtils.pickColor());
            sliceValue.setLabel(days[i]);
//            sliceValue.setTarget((float) Math.random() * 30 + 15);
            values.add(sliceValue);
        }

//        data = new PieChartData(values);
//        data.setHasLabels(true);
//        data.setHasLabelsOnlyForSelected(false);
//        data.setHasLabelsOutside(true);
//        data.setHasCenterCircle(false);
//
//        data.setSlicesSpacing(2);
//        data.setCenterText1("Hello!");

        // Get roboto-italic font.
//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//        data.setCenterText1Typeface(tf);
//        data.setCenterText1FontSize(30);

//        data.setCenterText2("Charts (Roboto Italic)");
//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//        data.setCenterText2Typeface(tf);
//        data.setCenterText2FontSize(22);
        data.setValues(values);

//        for (SliceValue value : data.getValues()) {
//            value.setTarget((float) Math.random() * 30 + 15);
//        }
        chart1.startDataAnimation();
        chart1.setChartRotation(new Random().nextInt(360),true);
    }

    private void initChartData(){

        chart1= (PieChartView) findViewById(R.id.chart1);

        data = new PieChartData();
        data.setHasLabels(true);
        data.setHasLabelsOnlyForSelected(false);
        data.setHasLabelsOutside(true);
//        chart1.setCircleFillRatio(0.8f);
        data.setValueLabelBackgroundEnabled(false);
        data.setHasCenterCircle(false);
        data.setValueLabelsTextColor(Color.argb(155,255,0,0));

        data.setSlicesSpacing(2);
        data.setCenterText1("Hello!");
        chart1.setPieChartData(data);
    }

    ColumnChartView chart2;
    ColumnChartData columnChartData;
    private void initColumnChar(){
        chart2= (ColumnChartView) findViewById(R.id.chart2);
        chart2.setZoomEnabled(false);

        columnChartData = new ColumnChartData();
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
        columnChartData.setAxisXBottom(axisX);
        columnChartData.setAxisYLeft(axisY);
        columnChartData.setFillRatio(0.2f);
        chart2.setColumnChartData(columnChartData);
    }

    public void initChart2(){
        int numValues = new Random().nextInt(7)+1;
        List<Column> columns = new ArrayList<Column>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numValues; ++i) {

            values = new ArrayList<>();
            values.add(new SubcolumnValue((float) Math.random() * 50f, ChartUtils.pickColor()));
            axisValues.add(new AxisValue(i).setLabel(""+i));
            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
        }

        columnChartData.getAxisXBottom().setValues(axisValues);
        columnChartData.setColumns(columns);
        chart2.startDataAnimation(1200);
    }
    @Override
    public void onRefresh() {
//        srf.setRefreshing(false);
    }
}
