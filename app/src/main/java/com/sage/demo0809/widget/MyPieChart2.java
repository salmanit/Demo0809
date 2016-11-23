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
import com.sage.demo0809.R;
import com.sage.demo0809.bean.TwitterOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sage on 2016/11/16.
 */

public class MyPieChart2 extends PieChart {
    public MyPieChart2(Context context) {
        super(context);
    }

    public MyPieChart2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPieChart2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public void initDefault(){
        setUsePercentValues(true);//显示百分比
        setDescription("");
        setExtraOffsets(5, 10, 5, 5);
        setDrawEntryLabels(false);//设置隐藏饼图上文字，只显示百分比
        setDragDecelerationFrictionCoef(0.95f);

//        setCenterTextTypeface(mTfLight);//字体
        setCenterText("");

        setDrawHoleEnabled(false);//中间是否为空
        setHoleColor(Color.WHITE);

        setTransparentCircleColor(Color.WHITE);
        setTransparentCircleAlpha(110);

        setHoleRadius(58f);
        setTransparentCircleRadius(61f);

        setDrawCenterText(true);

        setRotationAngle(0);
        // enable rotation of the chart by touch
        setRotationEnabled(true);
        setHighlightPerTapEnabled(true);
        setEntryLabelColor(Color.RED);
        setNoDataTextColor(Color.BLUE);
        // setUnit(" €");
        // setDrawUnitsInChart(true);

        // add a selection listener
//        setOnChartValueSelectedListener(this);

    }
    String[] lables={"A","B","C","D","E","F","G","H"};
    int[] color_choose={Color.RED,Color.GREEN,Color.GRAY,Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN,Color.GRAY};
    public void setData2(List<TwitterOptions> options, String question){
        if(options==null||options.size()==0){
            return;
        }

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        int interAlpha=255/options.size();
        for (int i = 0; i < options.size()&&i<lables.length ; i++) {
            TwitterOptions option=options.get(i);
            entries.add(new PieEntry(option.getCount(),lables[i]+option.getCount()));
//            colors.add(color_choose[i]);
            int color=caculateColor(Color.parseColor("#ff0000"),Color.parseColor("#ffffff"),i*1f/options.size());
            colors.add(getResources().getColor(R.color.colorPrimary));
        }

        PieDataSet dataSet = new PieDataSet(entries, "default");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors



//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        setData(data);

        // undo all highlights
        highlightValues(null);

        invalidate();
        animateY(1400, Easing.EasingOption.EaseInOutQuad);


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


    /**
     * 计算从startColor过度到endColor过程中百分比为franch时的颜色值
     * @param startColor 起始颜色 int类型
     * @param endColor 结束颜色 int类型
     * @param franch franch 百分比0.5
     * @return 返回int格式的color
     */
    public  int caculateColor(int startColor, int endColor, float franch){
        String strStartColor = "#" + Integer.toHexString(startColor);
        String strEndColor = "#" + Integer.toHexString(endColor);
        return Color.parseColor(caculateColor(strStartColor, strEndColor, franch));
    }

    /**
     * 计算从startColor过度到endColor过程中百分比为franch时的颜色值
     * @param startColor 起始颜色 （格式#FFFFFFFF）
     * @param endColor 结束颜色 （格式#FFFFFFFF）
     * @param franch 百分比0.5
     * @return 返回String格式的color（格式#FFFFFFFF）
     */
    public  String caculateColor(String startColor, String endColor, float franch){

        int startAlpha = Integer.parseInt(startColor.substring(1, 3), 16);
        int startRed = Integer.parseInt(startColor.substring(3, 5), 16);
        int startGreen = Integer.parseInt(startColor.substring(5, 7), 16);
        int startBlue = Integer.parseInt(startColor.substring(7), 16);

        int endAlpha = Integer.parseInt(endColor.substring(1, 3), 16);
        int endRed = Integer.parseInt(endColor.substring(3, 5), 16);
        int endGreen = Integer.parseInt(endColor.substring(5, 7), 16);
        int endBlue = Integer.parseInt(endColor.substring(7), 16);

        int currentAlpha = (int) ((endAlpha - startAlpha) * franch + startAlpha);
        int currentRed = (int) ((endRed - startRed) * franch + startRed);
        int currentGreen = (int) ((endGreen - startGreen) * franch + startGreen);
        int currentBlue = (int) ((endBlue - startBlue) * franch + startBlue);

        return "#" + getHexString(currentAlpha) + getHexString(currentRed)
                + getHexString(currentGreen) + getHexString(currentBlue);

    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    public  String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

}
