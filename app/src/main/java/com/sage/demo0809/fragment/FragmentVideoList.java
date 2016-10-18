package com.sage.demo0809.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sage.demo0809.R;
import com.sage.demo0809.ui.ActivityVideoList;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

/**
 * Created by Sage on 2016/10/11.
 */

public class FragmentVideoList extends Fragment {

RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_list,container,false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv= (RecyclerView) getView().findViewById(R.id.rv);

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(manager);
        rv.setAdapter(new MyRecyclerViewAdapter());
    }
    private String VIDEO_URL = "http://sunroam.imgup.cn/aerospace/bjc/19.mp4";

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder>{


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list,parent,false));
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, final int position) {
            holder.tvname.setText("jerry"+position);
            if(holder.mVideoView.canPause()){
                holder.mVideoView.pause();
            }
            holder.mVideoView.setMediaController(holder.mMediaController);
            holder.mVideoView.setVideoPath(VIDEO_URL);
            holder.mVideoView.seekTo(1000);
            holder.scale_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.mVideoView.canPause())
                            holder.mVideoView.pause();
                    ActivityVideoList activityVideoList= (ActivityVideoList) getActivity();
                    activityVideoList.position=position;
                    activityVideoList.url=VIDEO_URL;
                    activityVideoList.currentPlay=holder.mVideoView.getCurrentPosition();
                    Toast.makeText(v.getContext(),"click",Toast.LENGTH_SHORT).show();
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class MyHolder extends RecyclerView.ViewHolder{
            public UniversalMediaController mMediaController;
            public UniversalVideoView mVideoView;
            public View scale_button;
            public TextView tvname;
            public MyHolder(View itemView) {
                super(itemView);
                mMediaController= (UniversalMediaController) itemView.findViewById(R.id.media_controller);
               mVideoView= (UniversalVideoView) itemView.findViewById(R.id.videoView);
                scale_button=itemView.findViewById(R.id.scale_button);
                tvname= (TextView) itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
