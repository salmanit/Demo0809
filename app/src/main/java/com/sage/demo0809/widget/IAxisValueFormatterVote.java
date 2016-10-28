package com.sage.demo0809.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Sage on 2016/10/18.
 */

public class IAxisValueFormatterVote implements IAxisValueFormatter {
    public int last;
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int index= (int) value;
        if(index==0||index>last){
            return "";
        }
        return lables[index-1];
    }
    String[] lables={"A","B","C","D","E","F","G","H"};
    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
