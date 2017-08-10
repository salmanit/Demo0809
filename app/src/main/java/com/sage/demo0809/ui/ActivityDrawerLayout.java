package com.sage.demo0809.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.db.chart.view.BarChartView;
import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.fragment.FragmentDemo;

import org.apache.commons.lang3.StringEscapeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ActivityDrawerLayout extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

//        if(savedInstanceState==null){
//            getSupportFragmentManager().beginTransaction().replace(R.id.layout_left,new FragmentDemo(),"demo").commit();
//        }
//        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        setTitle("");
        initMyToolbar();
        findViewById(R.id.layout_root).setFitsSystemWindows(true);
        MyLog.i("----------------"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1475886888000l)));
        final EditText et= (EditText) findViewById(R.id.et_test);
        final TextView tvmain2= (TextView) findViewById(R.id.tv_main2);
        final TextView tvmain3= (TextView) findViewById(R.id.tv_main3);
        String temp="&#128176;&#128531;&#128176;&#128516;&#22909;&#21543;&#22909;&#21543;&#128522;";
//        et.setText(Html.fromHtml("&amp;#128563;&amp;#128516;&amp;#9917;&amp;#128516;"));
        temp="<p style=\"font-size: 16px;color: red\">1减肥时阶段性暴食，怎么治?<br/>5月不减肥，6月徒伤悲。<br/>天气一天天热起来，想必大家早已开始各自的减肥计划，想美美地迎接夏天了吧.我们都知道减肥的秘诀是管住嘴迈开腿，还需要</p>\n";
        et.setText(Html.fromHtml(temp));

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                String resutl=StringEscapeUtils.unescapeHtml4(Html.toHtml(et.getText()));
//                MyLog.i("======="+resutl);
//                TextView tvmain= (TextView) findViewById(R.id.tv_main);
//
////                tvmain.setText(Html.fromHtml(resutl));
//                tvmain.setText(Html.fromHtml(Html.escapeHtml(et.getText())));
//                /**第一步，将我们的带表情的字符串转换如下*/
//                /**&amp;#128176;&amp;#128531;&amp;#128176;&amp;#128516;&amp;#22909;&amp;#21543;&amp;#22909;&amp;#21543;&amp;#128522;*/
//                String result2=StringEscapeUtils.escapeHtml4(Html.escapeHtml(et.getText()));
//                    MyLog.i("00000==="+Html.escapeHtml(et.getText()));
//
//                MyLog.i("=====111="+result2);
//
//                MyLog.i("====222=="+StringEscapeUtils.unescapeHtml4(result2));
//
//                /**使用的时候这样处理*/
//                tvmain2.setText(Html.fromHtml(StringEscapeUtils.unescapeHtml4(result2)));

                String send=Html.escapeHtml(et.getText().toString());
                tvmain3.setText(send);
                tvmain2.setText(Html.fromHtml(send));
            }
        });

        assert et != null;
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("========"+s);
                String send=Html.escapeHtml(et.getText().toString());
                System.out.println("========"+send);
//                String  result=null;//调用过滤方法过滤；
//                if(!TextUtils.equals(result,s.toString())){
//                    et.setText(result);
//                }
            }
        });


        BarChartView barChartView= (BarChartView) findViewById(R.id.chart3);


        
        
    }
}
