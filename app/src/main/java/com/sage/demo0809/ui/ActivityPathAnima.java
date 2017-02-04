package com.sage.demo0809.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sage.demo0809.R;
import com.sage.demo0809.util.PathParserUtils;
import com.sage.demo0809.util.SvgPathParser;
import com.sage.demo0809.util.path.PathAnimView;
import com.sage.demo0809.util.path.StoreHouseAnimHelper;
import com.sage.demo0809.util.path.SysLoadAnimHelper;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sage on 2017/2/3.
 */

public class ActivityPathAnima extends ActivityBase {

    @BindView(R.id.pv1)
    PathAnimView pv1;
    @BindView(R.id.pv2)
    PathAnimView pv2;
    @BindView(R.id.pv3)
    PathAnimView pv3;
    @BindView(R.id.pv4)
    PathAnimView pv4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_anim);
        ButterKnife.bind(this);
        SvgPathParser svgPathParser = new SvgPathParser();
        try {
            pv1.setSourcePath(svgPathParser.parsePath("M1,1 L1,50 L50,50 L50,50 L50,1 Z"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pv1.setColorBg(Color.BLACK).setColorFg(Color.RED);
        pv1.setPathAnimHelper(new SysLoadAnimHelper(pv1,pv1.getSourcePath(),pv1.getAnimPath()));
        pv1.startAnim();

        try {
            pv2.setSourcePath(svgPathParser.parsePath("M1,1 L1,50 L50,50 L50,50 L50,1 Z"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pv2.setPathAnimHelper(new StoreHouseAnimHelper(pv2,pv2.getSourcePath(),pv2.getAnimPath()));
        pv2.startAnim();

        try {
            pv3.setSourcePath(svgPathParser.parsePath("M1,1 L1,50 L50,50 L50,50 L50,1 Z"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pv3.startAnim();

//        try {
//            pv4.setSourcePath(svgPathParser.parsePath("M1,1 L1,50 L50,50 L50,50 L50,1 Z"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        pv4.startAnim();
    }
}
