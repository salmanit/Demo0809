package com.sage.demo0809.ui;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.bean.TwitterOptions;
import com.sage.demo0809.widget.IAxisValueFormatterVote;
import com.sage.demo0809.widget.MyBarChart;
import com.sage.demo0809.widget.MyPieChart;
import com.sage.demo0809.widget.MyPieChart2;
import com.sage.demo0809.widget.XYMarkerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sage on 2016/10/18.
 */

public class ActivityChart extends ActivityBase implements OnChartValueSelectedListener {
    protected BarChart barChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initMyToolbar();
        myChartPie= (MyPieChart) findViewById(R.id.mychartpie);
        myChartPie2= (MyPieChart2) findViewById(R.id.mychartpie2);
        myBarChart= (MyBarChart) findViewById(R.id.mybarchart);

        barChart = (BarChart) findViewById(R.id.chart1);
        barChart.setOnChartValueSelectedListener(this);
        initBarChart();

        myChartPie2.initDefault();
        initPie(5);
        initMyChartPie(5);
    }

    public void refresh(View view){
        int size=new Random().nextInt(5)+1;
        initMyChartPie(size);
        setData2(size,10);
//        initPie(size);
    }


    private void initBarChart(){

        IAxisValueFormatterVote xFormater=new IAxisValueFormatterVote();
        barChart.setDrawBarShadow(false);//--绘制当前展示的内容顶部阴影
        barChart.setDrawValueAboveBar(true);//--绘制的图形都在bar顶部

        barChart.setDescription("description");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(80); //Y方向的最大值.

        // scaling can now only be done on x- and y-axis separately
//        barChart.setPinchZoom(false);  //--双指缩放.

        barChart.setDrawGridBackground(false);//--绘制中心内容区域背景色.
        // barChart.setDrawYLabels(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTfLight);//字体
        xAxis.setDrawGridLines(false);//--是否绘制竖直分割线.
        xAxis.setGranularity(1f); // only intervals of 1 day  底部label的分割间隙
        xAxis.setLabelCount(5);  //--对应的当前绘制在底部的label数
        xAxis.setValueFormatter(xFormater);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(6);
//        AxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setDrawGridLines(false); //-绘制水平分割线，按照当前Y方向的label点为起始点
        leftAxis.setLabelCount(8, false); //--绘制Y方向(应该)被显示的数量，第二个参数表示label是否是精准变化，还是近似变化
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//Y方向文字的位置，在线外侧.(默认在外侧)
        leftAxis.setSpaceTop(15f);  //分割线的间距百分比.
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)  Y方向的起始值.

//        YAxis rightAxis = barChart.getAxisRight();
//        rightAxis.setDrawGridLines(true); //-绘制水平分割线，按照当前Y方向的label点为起始点
//        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = barChart.getLegend();
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
        barChart.animateY(3000);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);

        XYMarkerView mv = new XYMarkerView(this, xFormater);
        mv.setChartView(barChart); // For bounds control
        barChart.setMarker(mv); // Set the marker to the chart
        setData(5, 80);
    }






    private MyPieChart myChartPie;
    private MyPieChart2 myChartPie2;
    MyBarChart myBarChart;
    private void initMyChartPie(int size){

        ArrayList<TwitterOptions> options=new ArrayList<>();
        for(int i=0;i<size;i++){
            TwitterOptions options1=new TwitterOptions();
            options1.setText("选项"+i);
            options1.setCount(new Random().nextInt(10));
            MyLog.i("i==="+i+"========"+options1.getCount());
            options1.value= (float) (Math.random() * 10);
            options.add(options1);
        }
//        setData22(options,"",size);
//        setData2(size,10);
        myBarChart.setData(options,"为啥天空这么蓝？");
        myChartPie.setData(options,"到底啥原因造成的鲸鱼集体自杀了？");
        myChartPie2.setData2(options,"到底啥原因造成的鲸鱼集体自杀了？");

    }
    private void setData(int count, float range) {

        float start = 0f;
//
//        barChart.getXAxis().setAxisMinValue(start);
//        barChart.getXAxis().setAxisMaxValue(start + count + 2);

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = (int) start; i < start + count ; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            BarEntry barEntry = new BarEntry(i + 1f, val);

            yVals1.add(barEntry);
        }

        BarDataSet set1;

        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");
            set1.setBarBorderWidth(1);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);


            data.setBarWidth(0.8f);//--设置bar的宽度 ,取值(0-1).




            barChart.setData(data);
        }
    }

    protected RectF mOnValueSelectedRectF = new RectF();



    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        if(e instanceof BarEntry){
            RectF bounds = mOnValueSelectedRectF;
            barChart.getBarBounds((BarEntry) e, bounds);
            MPPointF position = barChart.getPosition(e, YAxis.AxisDependency.LEFT);

            Log.i("bounds", bounds.toString());
            Log.i("position", position.toString());

            Log.i("x-index",
                    "low: " + barChart.getLowestVisibleX() + ", high: "
                            + barChart.getHighestVisibleX());

            MPPointF.recycleInstance(position);
        }
       if(e instanceof PieEntry){

       }
    }

    @Override
    public void onNothingSelected() {

    }




    private PieChart mChartPie;
    private void initPie(int size){
        mChartPie = (PieChart) findViewById(R.id.chart2);
        mChartPie.setUsePercentValues(true);//显示百分比
        mChartPie.setDescription("");
        mChartPie.setExtraOffsets(5, 10, 5, 5);

        mChartPie.setDragDecelerationFrictionCoef(0.95f);

//        mChartPie.setCenterTextTypeface(mTfLight);//字体
        mChartPie.setCenterText("");

        mChartPie.setDrawHoleEnabled(false);//中间是否为空
        mChartPie.setHoleColor(Color.WHITE);

        mChartPie.setTransparentCircleColor(Color.WHITE);
        mChartPie.setTransparentCircleAlpha(110);

        mChartPie.setHoleRadius(58f);
        mChartPie.setTransparentCircleRadius(61f);

        mChartPie.setDrawCenterText(true);

        mChartPie.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChartPie.setRotationEnabled(true);
        mChartPie.setHighlightPerTapEnabled(true);

        // mChartPie.setUnit(" €");
        // mChartPie.setDrawUnitsInChart(true);

        // add a selection listener
//        mChartPie.setOnChartValueSelectedListener(this);

        setData2(size, 100);

//        mChartPie.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChartPie.spin(2000, 0, 360);



    }

    private void setData2(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            float y=(float) ((Math.random() * mult) );
            entries.add(new PieEntry(y,"i"+y));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        mChartPie.setData(data);

        // undo all highlights
        mChartPie.highlightValues(null);

        mChartPie.invalidate();
        mChartPie.animateY(1400, Easing.EasingOption.EaseInOutQuad);


        Legend l = mChartPie.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChartPie.setEntryLabelColor(Color.WHITE);
//        mChartPie.setEntryLabelTypeface(mTfRegular);
        mChartPie.setEntryLabelTextSize(12f);
    }
    String[] lables={"A","B","C","D","E","F","G","H"};
    private void setData22(List<TwitterOptions> options, String question,int count) {


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

//        for (int i = 0; i < options.size() ; i++) {
//            TwitterOptions option=options.get(i);
//            entries.add(new PieEntry(option.value,"i"+option.value));
//        }
        for (int i = 0; i < count ; i++) {
            float y=(float) ((Math.random() * 10) );
//            y=options.get(i).value;
            entries.add(new PieEntry(y,"i"+y));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(mTfLight);
        mChartPie.setData(data);

        // undo all highlights
        mChartPie.highlightValues(null);

        mChartPie.invalidate();
        mChartPie.animateY(1400, Easing.EasingOption.EaseInOutQuad);


        Legend l = mChartPie.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChartPie.setEntryLabelColor(Color.RED);
//        mChartPie.setEntryLabelTypeface(mTfRegular);
        mChartPie.setEntryLabelTextSize(12f);
    }



}
