package com.sage.demo0809.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Sage on 2016/10/18.
 */

public class IAxisValueFormatterVote implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        return "选项一";
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
