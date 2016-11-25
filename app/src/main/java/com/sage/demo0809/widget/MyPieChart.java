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
import com.sage.demo0809.bean.TwitterOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sage on 2016/10/19.
 */

public class MyPieChart extends PieChart {
    public MyPieChart(Context context) {
        super(context);
        initDefault();
    }

    public MyPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDefault();
    }

    public MyPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault();
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


        
    }
    int[] color_choose={Color.parseColor("#FF0000"),
            Color.parseColor("#FF34B3"),
            Color.parseColor("#FF6347"),
            Color.parseColor("#FF8C69"),
            Color.parseColor("#FFAEB9"),
            Color.parseColor("#FFBBFF"),
            Color.parseColor("#FFD39B"),
            Color.parseColor("#FFEC8B")};
    String[] lables={"A","B","C","D","E","F","G","H"};
    public void setData(List<TwitterOptions> options, String question){

        setCenterText("");
        ArrayList<Integer> colors = new ArrayList<>();//颜色
        ArrayList<PieEntry> entries = new ArrayList<>();
        if(options==null||options.size()==0){
            return;
        }
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < options.size()&&i<lables.length ; i++) {
            TwitterOptions option=options.get(i);
            float value=option.getCount()==1?1.1f:option.getCount();
            entries.add(new PieEntry(value,lables[i]+value));
            colors.add(color_choose[i]);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        //百分比显示的位子以及属性处理。
        dataSet.setValueLinePart1OffsetPercentage(90.f);//这个应该是百分比在距离饼图边界的距离，100表示边界外，小于100文字就会在边界里了。
        dataSet.setValueLinePart1Length(0.3f);//这个是最短的指针线的长度，和饼图半径的百分比
        dataSet.setValueLinePart2Length(0.6f);//这个是最长的指针线的长度，和饼图半径的百分比
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLineColor(Color.DKGRAY);//线的颜色

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLUE);//那个百分比的字体颜色
        setData(data);

        // undo all highlights
        highlightValues(null);

        invalidate();
        afterDataSet();
    }
    private void afterDataSet(){
        animateY(900, Easing.EasingOption.EaseInOutQuad);
        // spin(2000, 0, 360);


        Legend l = getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        setEntryLabelColor(Color.WHITE);//百分比下的标签颜色
//        setEntryLabelTypeface(mTfRegular);
        setEntryLabelTextSize(12f);
    }
}
