package com.sage.demo0809.widget.lock;

/**
 * Created by Sage on 2017/8/10.
 * Description:
 */

public class RotateDegrees {

    public static float getDegrees(PointLock a, PointLock b) {
        float degrees = 0;
        float ax = a.getX();
        float ay = a.getY();
        float bx = b.getX();
        float by = b.getY();

        if (ax == bx) {
            if (by > ay) {
                degrees = 90;
            } else {
                degrees = 270;
            }
        } else if (by == ay) {
            if (ax > bx) {
                degrees = 180;
            } else {
                degrees = 0;
            }
        } else {
            if (ax > bx) {
                if (ay > by) { // 第三象限
                    degrees = 180 + (float) (Math.atan2(ay - by, ax - bx) * 180 / Math.PI);
                } else { // 第二象限
                    degrees = 180 - (float) (Math.atan2(by - ay, ax - bx) * 180 / Math.PI);
                }
            } else {
                if (ay > by) { // 第四象限
                    degrees = 360 - (float) (Math.atan2(ay - by, bx - ax) * 180 / Math.PI);
                } else { // 第一象限
                    degrees = (float) (Math.atan2(by - ay, bx - ax) * 180 / Math.PI);
                }
            }
        }
        return degrees;
    }
}
