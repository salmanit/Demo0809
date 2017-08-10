package com.sage.demo0809.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sage.demo0809.R;
import com.sage.demo0809.widget.StepLineChart;
import com.sage.demo0809.widget.StepPillarChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityTestStepLine extends AppCompatActivity {

    @BindView(R.id.stepChart)
    StepLineChart stepChart;
    @BindView(R.id.stepChart2)
    StepLineChart stepChart2;
    @BindView(R.id.stepPillar)
    StepPillarChart stepPillarChart;
    float[] data={5,15,45,12,9,2,30};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_step_line);
        ButterKnife.bind(this);

        stepChart.setAxisLineColor(Color.WHITE)
                .setCurveLineColor(Color.WHITE)
                .setToastTextColor(Color.parseColor("#7fbe25"));

        stepChart.setData(data);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stepChart.startMove();
            }
        },1000);

//        stepChart2.setAxisLineColor(Color.parseColor("#7fbe25"))
//                .setCurveLineColor(Color.parseColor("#7fbe25"))
//                .setToastTextColor(Color.parseColor("#7fbe25"))
//                .setDotPic(R.mipmap.dot_green);
//
//        stepChart2.setData(data);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                stepChart2.startMove();
//            }
//        },1000);


        stepPillarChart.setData(data);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stepPillarChart.startMove();
            }
        },1000);

    }

    int number=0;
    @OnClick(R.id.btn_refresh)
    void click(View view){
        if(number++%2==1){
            data=new float[]{0,0,45,0,0,0,0};
        }else{
            data=new float[]{0,0,45,0,40,0,40};
        }
        stepChart.setData(data);
//        stepChart2.setData(data);
        stepChart.startMove();
//        stepChart2.startMove();
    }

}
