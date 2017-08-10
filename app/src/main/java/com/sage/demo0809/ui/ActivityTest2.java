package com.sage.demo0809.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.sage.demo0809.R;
import com.sage.demo0809.widget.RelativeLayoutCustomState;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sage on 2016/12/16.
 */

public class ActivityTest2 extends ActivityBase {


    private static final String TAG = "aaaaaaaaaaaaaaaa";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_scale)
    ImageView ivScale;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;
    @BindView(R.id.iv_system)
    ImageView ivSystem;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;

    @BindView(R.id.rv_layout1)
    RelativeLayoutCustomState rv_layout1;
    @BindView(R.id.rv_layout2)
    RelativeLayoutCustomState rv_layout2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);

        rb1.setTextColor(createColor(Color.RED,Color.GRAY));
        rb2.setTextColor(createColor(Color.BLUE,Color.BLACK));
        if(!mOutput.exists()){
            mOutput.mkdirs();
        }
//        unzip(new File(Environment.getExternalStorageDirectory(),"CareAdd/zip/87000001_7.7_android.zip"));
//        mOutput=new File(Environment.getExternalStorageDirectory(),"/aaaaa");
//        unzip(new File(Environment.getExternalStorageDirectory(),"/87000007.zip"));


        rv_layout1.setMessageReaded(true);
        String[] arry1={"aaaa","bbbb"};
        String[] arry2={"cccc","ddddd","eeeee"};
        try {
            System.arraycopy(arry1,0,arry2,1,arry1.length);
            System.out.println("======="+ Arrays.toString(arry2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    File mOutput=new File(Environment.getExternalStorageDirectory(),"/aaa");
    private long unzip(File file) {
        long extractedSize = 0L;
        Enumeration<ZipEntry> entries;
        ZipFile zip = null;
        try {
            zip = new ZipFile(file);

            entries = (Enumeration<ZipEntry>) zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String path=entry.getName();

                if (entry.isDirectory()) {
//                    System.out.println("=============isDirectory=="+entry.getName());
                    continue;
                }else{
                    int index=path.lastIndexOf("/");
                    if(index!=-1){
                        path=path.substring(index);
                    }
                    System.out.println(path+"=============isDirectory not=="+entry.getName());
                }
                File destination = new File(mOutput.getAbsolutePath(),path);
                if (!destination.getParentFile().exists()) {
                    Log.e(TAG, "make=" + destination.getParentFile().getAbsolutePath());
                    destination.getParentFile().mkdirs();
                }

                OutputStream outStream = new FileOutputStream(destination);
                extractedSize += copy(zip.getInputStream(entry), outStream);
                outStream.close();
            }
        } catch (ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                zip.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return extractedSize;
    }
    private int copy(InputStream input, OutputStream output) {
        byte[] buffer = new byte[1024 * 8];
        BufferedInputStream in = new BufferedInputStream(input, 1024 * 8);
        BufferedOutputStream out = new BufferedOutputStream(output, 1024 * 8);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, 1024 * 8)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return count;
    }
    public int upZipFile(File zipFile, String folderPath)throws IOException {
        //public static void upZipFile() throws Exception{
        ZipFile zfile=new ZipFile(zipFile);
        Enumeration zList=zfile.entries();
        ZipEntry ze=null;
        byte[] buf=new byte[1024];
        while(zList.hasMoreElements()){
            ze=(ZipEntry)zList.nextElement();
//            if(ze.isDirectory()){
//                Log.d("upZipFile", "ze.getName() = "+ze.getName());
//                String dirstr = folderPath + ze.getName();
//                //dirstr.trim();
//                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
//                Log.d("upZipFile", "str = "+dirstr);
//                File f=new File(dirstr);
//                f.mkdir();
//                continue;
//            }
            Log.d("upZipFile", "ze.getName() = "+ze.getName());
            OutputStream os=new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
            InputStream is=new BufferedInputStream(zfile.getInputStream(ze));
            int readLen=0;
            while ((readLen=is.read(buf, 0, 1024))!=-1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
        Log.d("upZipFile", "finishssssssssssssssssssss");
        return 0;
    }

    public static File getRealFileName(String baseDir, String absFileName){
        String[] dirs=absFileName.split("/");
        File ret=new File(baseDir);
        String substr = null;
        if(dirs.length>1){
            for (int i = 0; i < dirs.length-1;i++) {
                substr = dirs[i];
                try {
                    //substr.trim();
                    substr = new String(substr.getBytes("8859_1"), "GB2312");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ret=new File(ret, substr);

            }
            Log.d("upZipFile", "1ret = "+ret);
            if(!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length-1];
            try {
                //substr.trim();
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                Log.d("upZipFile", "substr = "+substr);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ret=new File(ret, substr);
            Log.d("upZipFile", "2ret = "+ret);
            return ret;
        }
        return ret;
    }


    private ColorStateList createColor(int colorCheck, int colorNormal) {
        int[] colors = new int[]{colorCheck, colorNormal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        return colorStateList;
    }

    private void showAlert() {
        new AlertDialog.Builder(this).setView(R.layout.bottom_view)
                .setNegativeButton("cancel", null)
                .setPositiveButton("click", null).show();
    }

    boolean show;

    @OnClick(R.id.tv)
    public void onClick() {
        if (show) {
            try {
                showAlert();
                hideImageCircular();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                revealImageCircular();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        show = !show;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void hideImageCircular() {
        int x = ivScale.getWidth();
        int y = ivScale.getHeight();
        int radius = (int) Math.sqrt(x * x / 4 + y * y / 4);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(ivScale, x / 2, y / 2, radius, 0);

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ivScale.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
        Animation animation = AnimationUtils.makeOutAnimation(this, false);
        animation.setDuration(2000);
        ivSystem.startAnimation(animation);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealImageCircular() {
        int x = ivScale.getWidth();
        int y = ivScale.getHeight();
        int radius = (int) Math.sqrt(x * x / 4 + y * y / 4);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(ivScale, x / 2, y / 2, 0, radius);

        anim.setDuration(1000);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ivScale.setVisibility(View.VISIBLE);
            }
        });

        anim.start();

        Animation animation = AnimationUtils.makeInAnimation(this, false);
        animation.setDuration(2000);
        ivSystem.startAnimation(animation);
    }



}
