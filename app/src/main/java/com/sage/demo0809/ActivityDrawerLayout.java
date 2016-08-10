package com.sage.demo0809;

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

import com.sage.demo0809.fragment.FragmentDemo;

import org.apache.commons.lang3.StringEscapeUtils;

public class ActivityDrawerLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_left,new FragmentDemo(),"demo").commit();
        }
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        final EditText et= (EditText) findViewById(R.id.et_test);
        String temp="&amp;#128563;&amp;#128516;&amp;#9917;&amp;#128516;";
//        et.setText(Html.fromHtml("&amp;#128563;&amp;#128516;&amp;#9917;&amp;#128516;"));
//        et.setText(Html.fromHtml(StringEscapeUtils.escapeHtml4(Html.escapeHtml(temp))));

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resutl=StringEscapeUtils.unescapeHtml4(Html.toHtml(et.getText()));
                MyLog.i("======="+resutl);
                TextView tvmain= (TextView) findViewById(R.id.tv_main);
                tvmain.setText(Html.fromHtml(resutl));
                /**第一步，将我们的带表情的字符串转换如下*/
                /**&amp;#128176;&amp;#128531;&amp;#128176;&amp;#128516;&amp;#22909;&amp;#21543;&amp;#22909;&amp;#21543;&amp;#128522;*/
                String result2=StringEscapeUtils.escapeHtml4(Html.escapeHtml(et.getText()));

                MyLog.i("=====111="+result2);
                TextView tvmain2= (TextView) findViewById(R.id.tv_main2);
                MyLog.i("====222=="+StringEscapeUtils.unescapeHtml4(result2));

                /**使用的时候这样处理*/
                tvmain2.setText(Html.fromHtml(StringEscapeUtils.unescapeHtml4(result2)));
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
//                String  result=null;//调用过滤方法过滤；
//                if(!TextUtils.equals(result,s.toString())){
//                    et.setText(result);
//                }
            }
        });
    }
}
