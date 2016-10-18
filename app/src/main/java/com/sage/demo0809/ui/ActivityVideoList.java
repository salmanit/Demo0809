package com.sage.demo0809.ui;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;
import com.sage.demo0809.fragment.FragmentVideoList;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

/**
 * Created by Sage on 2016/10/11.
 */

public class ActivityVideoList extends ActivityBase {

    private static final String TAG = "qqqqqqq";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,fragmentVideoList=new FragmentVideoList(),"aaa").commit();
        }else{
            fragmentVideoList= (FragmentVideoList) getSupportFragmentManager().findFragmentByTag("aaa");
        }
    }

    FragmentVideoList fragmentVideoList;
    public int position;
    public String url;
    public int currentPlay;
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config); MyLog.i("========================00");
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            MyLog.i("========================hengping");
            setContentView(R.layout.activity_video_list);
            init();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            MyLog.i("========================shuping");
            setContentView(R.layout.activity_video_list);
                fragmentVideoList=new FragmentVideoList();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,fragmentVideoList,"aaa").commit();
        }
    }
    UniversalMediaController mMediaController;
    UniversalVideoView mVideoView;
    private void init(){
         mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
         mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        View scale_button = findViewById(R.id.scale_button);
        mVideoView.setMediaController(mMediaController);
        scale_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"click22",Toast.LENGTH_SHORT).show();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        startPlay();
    }
    private void startPlay(){
        if(mVideoView.canPause()){
            mVideoView.pause();
        }
        MyLog.i("url=="+url+"==="+currentPlay);
        mVideoView.setVideoPath(url);
        mVideoView.start();
        mVideoView.seekTo(currentPlay);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaController.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else
            super.onBackPressed();
    }
}
