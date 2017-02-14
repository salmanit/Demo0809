package com.sage.demo0809.ui;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.widget.MyChartView;
import com.sage.demo0809.widget.RelativeLayoutWithBg;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sage on 2016/12/16.
 */

public class ActivityTest1 extends ActivityBase {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.my_chart_view)
    MyChartView myChartView;
    private ArrayList<Float> chartList;
    private LinearLayout llChart;
    private RelativeLayout relativeLayout;

    RelativeLayoutWithBg rv;
    int count=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.bind(this);
        initChatView();

        WebView webView= (WebView) findViewById(R.id.wv);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                showToast("title=="+title);
            }
        });
        webView.loadUrl("http://www.bejson.com/");

         rv= (RelativeLayoutWithBg) findViewById(R.id.rv_with_bg);
//        rv.setArrowWidth(50).setArrowHeight(50).setColorBG(Color.BLUE).invalidate();
        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                rv.setDirection(count%4);
            }
        });
        showToast("version=="+getVerName());
    }
    public  String getVerName() {
        String verName = "0.1";
        try {
            verName = getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
    private void initChatView() {

        myChartView.setLefrColorBottom(getResources().getColor(R.color.leftColorBottom));
        myChartView.setLeftColor(getResources().getColor(R.color.leftColor));
        myChartView.setRightColor(getResources().getColor(R.color.rightColor));
        myChartView.setRightColorBottom(getResources().getColor(R.color.rightBottomColor));
        myChartView.setSelectLeftColor(getResources().getColor(R.color.selectLeftColor));
        myChartView.setSelectRightColor(getResources().getColor(R.color.selectRightColor));
        myChartView.setLineColor(getResources().getColor(R.color.xyColor));
        chartList = new ArrayList<>();

        relativeLayout = (RelativeLayout) findViewById(R.id.linearLayout);
        relativeLayout.removeView(llChart);
        Random random = new Random();
        while (chartList.size() < 24) {
            int randomInt = random.nextInt(100);
            chartList.add((float) randomInt);
        }
        myChartView.setList(chartList);
        myChartView.setListener(new MyChartView.getNumberListener() {
            @Override
            public void getNumber(int number, int x, int y) {
                relativeLayout.removeView(llChart);
                //反射加载点击柱状图弹出布局
                llChart = (LinearLayout) LayoutInflater.from(ActivityTest1.this).inflate(R.layout.layout_shouru_zhichu, null);
                TextView tvZhichu = (TextView) llChart.findViewById(R.id.tv_zhichu);
                TextView tvShouru = (TextView) llChart.findViewById(R.id.tv_shouru);
                tvZhichu.setText((number + 1) + "月支出" + " " + chartList.get(number * 2));
                tvShouru.setText("收入: " + chartList.get(number * 2 + 1));
                llChart.measure(0, 0);//调用该方法后才能获取到布局的宽度
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = x - 100;
                if (x - 100 < 0) {
                    params.leftMargin = 0;
                } else if (x - 100 > relativeLayout.getWidth() - llChart.getMeasuredWidth()) {
                    //设置布局距左侧屏幕宽度减去布局宽度
                    params.leftMargin = relativeLayout.getWidth() - llChart.getMeasuredWidth();
                }
                llChart.setLayoutParams(params);
                relativeLayout.addView(llChart);
            }
        });
    }


    @OnClick({R.id.tv_load,R.id.tv_cancel_})
    public  void clickView(View view){
        switch (view.getId()){
            case R.id.tv_load:
                showToast("开始更新,新版本0207");
                File file = new File(Environment.getExternalStorageDirectory(), "/p.apk");
                MyLog.i("p.apk path=======" + file.getAbsolutePath());
                if(file.exists())
                    TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), file.getAbsolutePath());
                break;
            case R.id.tv_cancel_:
                Tinker.with(getApplicationContext()).cleanPatch();
                break;
        }
    }
}
