package com.sage.demo0809.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sage.demo0809.R;
import com.sage.demo0809.widget.StepLineChart;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTestStepLine extends AppCompatActivity {

    @BindView(R.id.stepChart)
    StepLineChart stepChart;
    float[] data={5,15,15,12,9,2,30};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_step_line);
        ButterKnife.bind(this);

        stepChart.setData(data);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stepChart.startMove();
            }
        },1000);

    }



}
