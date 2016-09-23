package com.sage.demo0809.widget;

/**
 * Created by Sage on 2016/8/19.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.sage.demo0809.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BubbleLayout extends View {

    private Random random = new Random();
    private int width, height;
    private Paint paint;
    private int maxBubble=10;/**屏幕中泡泡最多显示的个数*/
    private int maxRadius=50;/**泡泡的最大半径*/
    private int initRadius=30;/**泡泡的初始生成的最大半径*/
    private final List<Bubble> bubbles;/**存储泡泡的数据*/
    private float speedFactor=3;/**我们默认每次移动1以下的距离，这里可以增加速度*/
    private boolean show=true;/**是否展示泡泡*/
    public void stop(){
        bubbles.clear();
        show=false;
        invalidate();
    }
    public void reset(){
        bubbles.clear();
        show=true;
        invalidate();
    }
    public BubbleLayout(Context context) {
        super(context);
        init();
        bubbles = new ArrayList<>(maxBubble);
    }

    public BubbleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        bubbles = new ArrayList<>(maxBubble);
    }

    public BubbleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        bubbles = new ArrayList<>(maxBubble);
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        // 绘制气泡
        float bRadius;
        float bX, bY, speedX, speedY;
        List<Bubble> delList = new ArrayList<>();
        synchronized (bubbles) {
            for (Bubble bubble : bubbles) {
                bX = bubble.getX();
                bY = bubble.getY();
                speedX = bubble.getSpeedX();
                speedY = bubble.getSpeedY();
                bRadius = bubble.getRadius();
                if(bRadius<maxRadius){
                    bRadius=bRadius+(bubble.originalY-bY)/100f;
                }
                if (bY - speedY <= -bRadius || bX + speedX <= -bRadius || bX + speedX >= width + bRadius) {
                    delList.add(bubble);
                } else {
                    int i = bubbles.indexOf(bubble);
                    bubble.setX(bX + speedX*speedFactor);
                    bubble.setY(bY - speedY*speedFactor);
                    bubbles.set(i, bubble);
                    paint.setShader(new RadialGradient(bX, bY, bRadius,
                            getResources().getColor(R.color.transparent), getResources().getColor(R.color.white_transparent), Shader.TileMode.CLAMP));
                    canvas.drawCircle(bX, bY, bRadius, paint);
                }
            }
            bubbles.removeAll(delList);
        }
        if(show)
            postInvalidateDelayed(20);
        bubbleFarm();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float downX = event.getX();
        float downY = event.getY();
        Bubble iBubble = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float bX, bY;
                float bRadius;
                synchronized (bubbles) {
                    for (Bubble bubble : bubbles) {
                        bX = bubble.getX();
                        bY = bubble.getY();
                        bRadius = bubble.getRadius();
                        if (downX >= bX - bRadius && downX <= bX + bRadius && downY >= bY - bRadius && downY <= bY + bRadius) {
                            iBubble = bubble;
                            break;
                        }
                    }
                    if (iBubble != null && iBubble.getRadius() >= maxRadius && bubbles.size() < maxBubble) {
                        float iX = iBubble.getX(), iY = iBubble.getY();
                        float iSpeedX = iBubble.getSpeedX() * 1.5f, iSpeedY = iBubble.getSpeedY() * 1.5f;
                        float iRadius = iBubble.getRadius() / 2;
                        Bubble b1 = new Bubble();
                        b1.setRadius(iRadius);
                        b1.setX(iX - iRadius);
                        b1.setY(iY);
                        b1.originalY=iY;
                        b1.setSpeedX(iSpeedX > 0 ? -iSpeedX : iSpeedX);
                        b1.setSpeedY(iSpeedY);
                        Bubble b2 = new Bubble();
                        b2.setRadius(iRadius);
                        b2.setX(iX + iRadius);
                        b2.setY(iY);
                        b2.originalY=iY;
                        b2.setSpeedX(iSpeedX > 0 ? iSpeedX : -iSpeedX);
                        b2.setSpeedY(iSpeedY);
                        bubbles.remove(iBubble);
                        bubbles.add(b1);
                        bubbles.add(b2);
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 生产气泡
     */
    private void bubbleFarm() {
        if(bubbles.size()>=maxBubble){
            return;
        }
//        while (true) {
//            if (isPause) {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                isPause = bubbles.size() >= maxBubble;
//
//                continue;
//            }
//            try {
//                Thread.sleep((random.nextInt(3) + 1) * 200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Bubble bubble = new Bubble();
            // 随机取得气泡半径
            int radius = random.nextInt(initRadius);
            while (radius == 0) {
                radius = random.nextInt(initRadius);
            }
            // 随机取得X轴速度（x轴速度可为正数也可为负数，正数向右移，负数向左移）
            float speedX = random.nextFloat() - 0.5f;
            while (speedX == 0) {
                speedX = random.nextFloat() - 0.5f;
            }
            // 随机取得Y轴速度
            float speedY = random.nextFloat() * 5;
            while (speedY < 1) {
                speedY = random.nextFloat() * 5;
            }
            // 气泡半径
            bubble.setRadius(radius);
            // 气泡X轴速度（平移速度）
            bubble.setSpeedX(speedX * 2);
            // 气泡Y轴速度(上升速度)
            bubble.setSpeedY(speedY);
            // 气泡X轴坐标
//            int symbol = random.nextInt(2) > 0 ? 1 : -1;
//            bubble.setX(width / 2 + symbol * (width / 3) * random.nextFloat());
            bubble.setX(width/2);
            // 气泡Y轴坐标
            bubble.setY(height);
            bubble.originalY=height;
            bubbles.add(bubble);
//            synchronized (bubbles) {
//                if (bubbles.size() < maxBubble) {
//                    bubbles.add(bubble);
//                    isPause = false;
//                } else {
//                    isPause = true;
//                }
//            }

    }

    private class Bubble {
        /**
         * 气泡半径
         */
        private float radius;
        /**
         * 上升速度
         */
        private float speedY;
        /**
         * 平移速度
         */
        private float speedX;
        /**
         * 气泡x坐标
         */
        private float x;
        /**
         * 气泡y坐标
         */
        private float y;

        public float originalY;/**初始的y坐标*/
        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getSpeedY() {
            return speedY;
        }

        public void setSpeedY(float speedY) {
            this.speedY = speedY;
        }

        public float getSpeedX() {
            return speedX;
        }

        public void setSpeedX(float speedX) {
            this.speedX = speedX;
        }

    }
}
