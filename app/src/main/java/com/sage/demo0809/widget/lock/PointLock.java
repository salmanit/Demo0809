package com.sage.demo0809.widget.lock;

/**
 * Created by Sage on 2017/8/10.
 * Description:
 */

public class PointLock {

    public static int BITMAP_NORMAL = 0; // 正常
    public static int BITMAP_ERROR = 1;  // 错误
    public static int BITMAP_PRESS = 2;  // 按下

    //九宫格中的点的下标（即每个点代表一个值）
    private String index;
    //点的状态
    private int state;
    //点的坐标
    private float x;
    private float y;

    public PointLock() {
        super();
    }

    public PointLock(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getIndex() {
        return index;
    }

    public int getState() {
        return state;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    /**
     * 判断屏幕上的九宫格中的点能否可以进行连线
     *
     * @param a point
     * @param moveX touchX
     * @param moveY touchY
     * @param radius 点bitmap的半径
     * @return 布尔型
     */
    public boolean isWith(PointLock a, float moveX, float moveY, float radius) {
        float result = (float) Math.sqrt((a.getX() - moveX)
                * (a.getX() - moveX) + (a.getY() - moveY)
                * (a.getY() - moveY));
        if (result <=  radius ) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[index:"+index+";x="+x+";y="+y+"]";
    }
}
