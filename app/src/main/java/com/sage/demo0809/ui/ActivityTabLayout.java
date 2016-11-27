package com.sage.demo0809.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.webkit.WebView;

import com.sage.demo0809.R;
import com.sage.demo0809.widget.WebViewVideoFull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sage on 2016/11/24.
 */

public class ActivityTabLayout extends ActivityBase {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.wv)
    WebViewVideoFull wv;
    @BindView(R.id.wv2)
    WebView wv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        ButterKnife.bind(this);
        initMyToolbar();
//        wv.loadUrl("http://voip.vtc365.com/LiveVideoServer/m_liveVideoRoom.do?groupId=731&key=fLmlBp02FK81SZ65AYui");
        wv2.getSettings().setJavaScriptEnabled(true);
        wv2.loadUrl("http://voip.vtc365.com/LiveVideoServer/play4ThirdParty.do?video.videoId=346721");
    }


}
