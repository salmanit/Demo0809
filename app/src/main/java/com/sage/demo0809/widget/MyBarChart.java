package com.sage.demo0809.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.sage.demo0809.bean.TwitterOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sage on 2016/10/19.
 */

public class MyBarChart extends BarChart {
    public MyBarChart(Context context) {
        super(context);
        initDefault();
    }

    public MyBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefault();
    }

    public MyBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDefault();
    }

    IAxisValueFormatterVote xFormater=new IAxisValueFormatterVote();
    private void initDefault(){

        setDrawBarShadow(false);//--绘制当前展示的内容顶部阴影
        setDrawValueAboveBar(true);//--绘制的图形都在bar顶部

        setDescription("");//description

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        setMaxVisibleValueCount(80); //Y方向的最大值.

        // scaling can now only be done on x- and y-axis separately
//        setPinchZoom(false);  //--双指缩放.

        setDrawGridBackground(false);//--绘制中心内容区域背景色.
        // setDrawYLabels(false);

        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTfLight);//字体
        xAxis.setDrawGridLines(false);//--是否绘制竖直分割线.
        xAxis.setGranularity(1f); // only intervals of 1 day  底部label的分割间隙
        xAxis.setLabelCount(5);  //--对应的当前绘制在底部的label数
        xAxis.setValueFormatter(xFormater);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(9);
//        AxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setDrawGridLines(false); //-绘制水平分割线，按照当前Y方向的label点为起始点
        leftAxis.setLabelCount(5, false); //--绘制Y方向(应该)被显示的数量，第二个参数表示label是否是精准变化，还是近似变化
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//Y方向文字的位置，在线外侧.(默认在外侧)
        leftAxis.setSpaceTop(15f);  //分割线的间距百分比.
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)  Y方向的起始值.

        getAxisRight().setEnabled(false);
//        YAxis rightAxis = getAxisRight();
//        rightAxis.setDrawGridLines(true); //-绘制水平分割线，按照当前Y方向的label点为起始点
//        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = getLegend();
        l.setForm(Legend.LegendForm.SQUARE); //--设置legend的形状.
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT); //--设置legend的位置.
        l.setFormSize(12f);            //--设置legend的大小
        l.setTextSize(12f);               //--设置legend上的文字大小
//        l.setXEntrySpace(100f);
        l.setYOffset(30f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        animateY(900);
        setPinchZoom(false);
        setDoubleTapToZoomEnabled(false);

//        XYMarkerView mv = new XYMarkerView(getContext(), xFormater);
//        mv.setChartView(this); // For bounds control
//        setMarker(mv); // Set the marker to the chart
    }
    String[] lables={"A","B","C","D","E","F","G","H"};
    int[] color_choose={Color.parseColor("#FF0000"),
            Color.parseColor("#FF34B3"),
            Color.parseColor("#FF6347"),
            Color.parseColor("#FF8C69"),
            Color.parseColor("#FFAEB9"),
            Color.parseColor("#FFBBFF"),
            Color.parseColor("#FFD39B"),
            Color.parseColor("#FFEC8B")};
    public  void setData(List<TwitterOptions> options, String question) {

        xFormater.last=options.size();
        getXAxis().setLabelCount(options.size());
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        int[] colors=new int[options.size()];
        for (int i = 0; i < options.size()&&i<color_choose.length ; i++) {
            TwitterOptions temp=options.get(i);
            BarEntry barEntry = new BarEntry(i + 1f,temp.getCount());
            yVals1.add(barEntry);
            colors[i]= color_choose[i];
        }

        BarDataSet set1;
        getAxisLeft().setLabelCount(2,true);
        if (getData() != null &&
                getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) getData().getDataSetByIndex(0);
            set1.setStackLabels(lables);
            set1.setValues(yVals1);
            set1.setColors(colors);
            getData().notifyDataChanged();
            notifyDataSetChanged();
        } else
        {
            set1 = new BarDataSet(yVals1, "");
            set1.setBarBorderWidth(1);
            set1.setColors(colors);
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return (int)value+"";
                }
            });
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.8f);//--设置bar的宽度 ,取值(0-1).
            setData(data);
        }
        invalidate();
    }
    
    
}
