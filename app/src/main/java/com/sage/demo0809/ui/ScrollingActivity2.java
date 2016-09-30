package com.sage.demo0809.ui;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.StateListDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sage.demo0809.MyLog;
import com.sage.demo0809.R;

public class ScrollingActivity2 extends ActivityBase {

    private TextView tv_top;
    private ImageView iv_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setContentInsetsAbsolute(0,0);
//        toolbar.setNavigationIcon(R.drawable.lib_btn_back);
        findViewById(R.id.toolbar_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goNextCla();
            }
        });
        
        iv_next= (ImageView) findViewById(R.id.iv_next);
        tv_top= (TextView) findViewById(R.id.tv_top);
        layout_rotate=findViewById(R.id.layout_rotate);
//        final SensorManager mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        PackageManager packageManager = getPackageManager();
//        MyLog.i(packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
//                +"========="+packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR));
//        final Sensor detectorSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        boolean result=mySensorManager.registerListener(listener, detectorSensor, SensorManager.SENSOR_DELAY_UI);
//
//        MyLog.i("==support=="+result+"==="+detectorSensor.getVendor()+"=="+detectorSensor.getPower()+"=="+detectorSensor.getType());
//        new Thread(new Runnable() {
//            public void run() {
//                boolean result=mySensorManager.registerListener(listener, detectorSensor, SensorManager.SENSOR_DELAY_UI);
//
//                MyLog.i("==support=="+result+"==="+detectorSensor.getVendor()+"=="+detectorSensor.getPower()+"=="+detectorSensor.getType());
//            }
//        }).start();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void goNextCla(){
        Intent intent = new Intent(this,ScrollingActivity.class);
        ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeThumbnailScaleUpAnimation(findViewById(Window.ID_ANDROID_CONTENT)
                , BitmapFactory.decodeResource(getResources(),R.drawable.bangdan_sl_img),
                findViewById(Window.ID_ANDROID_CONTENT).getWidth()-100,findViewById(Window.ID_ANDROID_CONTENT).getHeight()-100);
//        activityOptionsCompat=ActivityOptionsCompat.makeThumbnailScaleUpAnimation(layout_rotate,
//                BitmapFactory.decodeResource(getResources(),R.drawable.bangdan_vs_img),layout_rotate.getWidth()/2,layout_rotate.getHeight()/2);
//        activityOptionsCompat=ActivityOptionsCompat.makeScaleUpAnimation(findViewById(Window.ID_ANDROID_CONTENT),
//                findViewById(Window.ID_ANDROID_CONTENT).getWidth()-100,findViewById(Window.ID_ANDROID_CONTENT).getHeight()-100
//        ,100,100);
//        activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,findViewById(R.id.layout_rotate),"shareNames");
//        activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,
//                new Pair<View, String>(findViewById(R.id.layout_rotate),"shareNames")
//                ,new Pair<View, String>(findViewById(R.id.btn_fragment),"love"));
        activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent,activityOptionsCompat.toBundle());
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,findViewById(R.id.layout_rotate),"shareNames").toBundle());
    }
    
    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            MyLog.i("======1=======计步传感器");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            MyLog.i("onAccuracyChanged=============计步传感器"+accuracy);
        }
    };
    RotateAnimation animation;
    View layout_rotate;
    private void ainim1(){
        StateListDrawable drawable=new StateListDrawable();
         animation=new RotateAnimation(0,360,layout_rotate.getWidth()/2f,layout_rotate.getHeight()/2f);
        animation.setDuration(4000);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        layout_rotate.startAnimation(animation);
    }


    public void top(View view){
        intro(view);
    }
    public void intro(View view) {
        ainim1();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(findViewById(R.id.scroll));
        if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            tv_top.setText("top");
        }else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            tv_top.setText("bottom");
        }
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    public void select(View view) {
        animation.cancel();
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(this)
                .inflate(R.layout.list, null);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(recyclerView);
        dialog.show();
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                Toast.makeText(ScrollingActivity2.this, text, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    static class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private OnItemClickListener mItemClickListener;

        public void setOnItemClickListener(OnItemClickListener li) {
            mItemClickListener = li;
        }

        @Override
        public Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new Holder(item);
        }

        @Override
        public void onBindViewHolder(final Adapter.Holder holder, int position) {
            holder.tv.setText("item " + position);
            if(mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(holder.getLayoutPosition(),
                                holder.tv.getText().toString());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView tv;
            public Holder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.text);
            }
        }

        interface OnItemClickListener {
            void onItemClick(int position, String text);
        }
    }



    public void fragment(View view){
        new MyBottomSheetDialogFragment().show(getSupportFragmentManager(),"111");
    }

    String[] lists={"1111","2222","3333","1111","2222","3333","1111","2222",
            "3333","1111","2222","3333","1111","2222","3333","1111","2222","3333","1111","2222","3333"
            ,"1111","2222","3333","1111","2222","3333","1111","2222","3333","1111","2222","3333"
            ,"1111","2222","3333","1111","2222","3333","1111","2222","3333","1111","2222","3333"};

    public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment{

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.bottom_view,container,false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            ListView lv= (ListView) getView().findViewById(R.id.lv);
            lv.setAdapter(new ArrayAdapter<String>(getView().getContext(),android.R.layout.simple_list_item_1,lists));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   dismiss();
                }
            });
        }
    }
}
