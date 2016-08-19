package com.sage.demo0809;

/**
 * Created by Sage on 2016/8/17.
 */
import java.io.InputStream;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.ImageView;
import org.simple.eventbus.EventBus;

public class PowerImageView extends ImageView  {
    /*播放GIF动画的关键类*/
    private Movie mMovie;


    /*记录动画开始的时间*/
    private long mMovieStart;

    public PowerImageView(Context context) {
        super(context);
        //关闭硬件加速才行，或者布局里添加android:layerType="software"
        setLayerType(LAYER_TYPE_SOFTWARE,new Paint());
    }
    /**
     * PowerImageView构造函数。
     * @param context
     */
    public PowerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //关闭硬件加速才行，或者布局里添加android:layerType="software"
        setLayerType(LAYER_TYPE_SOFTWARE,new Paint());
    }
    /**
     * PowerImageView构造函数，在这里完成所有必要的初始化操作。
     * @param context
     */
    public PowerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //关闭硬件加速才行，或者布局里添加android:layerType="software"
        setLayerType(LAYER_TYPE_SOFTWARE,new Paint());
    }

    public void startPlay(int resourceId){
        InputStream is = getResources().openRawResource(resourceId);
        // 使用Movie类对流进行解码
        mMovie = Movie.decodeStream(is);
        if(mMovie==null){
            setImageResource(resourceId);
        }else{
            invalidate();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (mMovie == null) {
            // mMovie等于null，说明是张普通的图片，则直接调用父类的onDraw()方法
            super.onDraw(canvas);
        } else {
            // mMovie不等于null，说明是张GIF图片
                // 如果允许自动播放，就调用playMovie()方法播放GIF动画
               boolean result= playMovie(canvas);
            if(result){
                EventBus.getDefault().post("","movie_finish");
            }
            postInvalidateDelayed(100);
        }
    }


    /**
     * 开始播放GIF动画，播放完成返回true，未完成返回false。
     * @param canvas 画布
     * @return 播放完成返回true，未完成返回false。
     */
    private boolean playMovie(Canvas canvas) {
        long now = SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        int duration = mMovie.duration();MyLog.i("===="+duration);
        if (duration == 0) {
            duration = 1000;
        }
        int relTime = (int) ((now - mMovieStart) % duration);
        mMovie.setTime(relTime);
        mMovie.draw(canvas, 0, 0);
        if ((now - mMovieStart) >= duration) {
            mMovieStart = 0;
            return true;
        }
        return false;
    }

}

