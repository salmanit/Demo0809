package com.sage.demo0809.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sage.demo0809.bean.TwitterOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sage on 2016/10/19.
 */

public class MyPieChart extends PieChart{
    public MyPieChart(Context context) {
        super(context);
//        initDefault();
    }

    public MyPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        initDefault();
    }

    public MyPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initDefault();
    }

    public void initDefault(){
        setUsePercentValues(true);//显示百分比
        setDescription("");
        setExtraOffsets(5, 10, 5, 5);

        setDragDecelerationFrictionCoef(0.95f);

//        setCenterTextTypeface(mTfLight);//字体


        setDrawHoleEnabled(false);//中间是否为空
        setHoleColor(Color.WHITE);

        setTransparentCircleColor(Color.WHITE);
        setTransparentCircleAlpha(110);

        setHoleRadius(48f);
        setTransparentCircleRadius(51f);

        setDrawCenterText(true);

        setRotationAngle(0);
        // enable rotation of the chart by touch
        setRotationEnabled(true);
        setHighlightPerTapEnabled(true);

        // setUnit(" €");
        // setDrawUnitsInChart(true);

        // add a selection listener
        setOnChartValueSelectedListener(null);


//        Legend l = getLegend();
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//
//        // entry label styling
//        setEntryLabelColor(Color.WHITE);
////        setEntryLabelTypeface(mTfRegular);
//        setEntryLabelTextSize(12f);
    }
    boolean first=true;
    String[] lables={"A","B","C","D","E","F","G","H"};
    int[] color_choose={Color.RED,Color.GREEN,Color.GRAY,Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN,Color.GRAY};
    public void setData(List<TwitterOptions> options,String question){
        if(first)
        initDefault();
        first=false;
        setCenterText("");
        ArrayList<Integer> colors = new ArrayList<>();//颜色
        ArrayList<PieEntry> entries = new ArrayList<>();
        if(options==null||options.size()==0){
            return;
        }
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        int interAlpha=255/options.size();
        for (int i = 0; i < options.size()&&i<lables.length ; i++) {
            TwitterOptions option=options.get(i);
            entries.add(new PieEntry(option.getCount(),lables[i]));
            colors.add(color_choose[i]);
        }


        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        setData(data);

        // undo all highlights
        highlightValues(null);

        invalidate();
        afterDataSet();
    }
    private void afterDataSet(){
        animateY(1400, Easing.EasingOption.EaseInOutQuad);
//         spin(2000, 0, 360,Easing.EasingOption.EaseInOutQuad);

        Legend l = getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        setEntryLabelColor(Color.WHITE);
//        setEntryLabelTypeface(mTfRegular);
        setEntryLabelTextSize(12f);

    }
}
