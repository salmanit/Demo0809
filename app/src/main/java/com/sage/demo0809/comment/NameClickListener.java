package com.sage.demo0809.comment;

import android.text.SpannableString;
import com.sage.demo0809.MyLog;


/**
 *
 * @ClassName: NameClickListener
 * @Description: 点赞和评论中人名的点击事件
 * @author yiw
 * @date 2015-01-02 下午3:42:21
 *
 */
public class NameClickListener implements ISpanClick {
    private SpannableString userName;
    private String userId;

    public NameClickListener(SpannableString name, String userid) {
        this.userName = name;
        this.userId = userid;
    }

    @Override
    public void onClick(int position) {
//        Toast.makeText(MyApplication.getContext(), userName + " &id = " + userId, Toast.LENGTH_SHORT).show();
        MyLog.i("-------------"+userName + " &id = " + userId);
    }
}
