package com.zwb.next.util;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.content.res.Resources;

public class DisplayUtils {

    /**
     * 屏幕宽度，横向像素点个数
     */
    private int mScreenWidth;

    /**
     * 屏幕高度，纵向像素点个数
     */
    private int mScreenHeight;

    /**
     * 屏幕分辨率
     */
    private Point mScreenResolution;

    /**
     * 屏幕密度,dots-per-inch
     */
    private int mDensityDpi;

    /**
     * 缩放系数
     */
    private float mScaleFactor;
    private float mFontScaleFactor;
    private float mXdpi;
    private float mYdpi;

    private static DisplayUtils mDisplayUtil;

    private DisplayUtils() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        mScreenResolution = new Point(dm.widthPixels, dm.heightPixels);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mDensityDpi = dm.densityDpi;
        mXdpi = dm.xdpi;
        mYdpi = dm.ydpi;
        mScaleFactor = dm.density;
        mFontScaleFactor = dm.scaledDensity;
    }

    private static void getInstance() {
        if (mDisplayUtil == null) {
            synchronized (DisplayUtils.class) {
                if (mDisplayUtil == null) {
                    mDisplayUtil = new DisplayUtils();
                }
            }
        }
    }

    public static int dp2px(float dpValue) {
        if (mDisplayUtil == null) {
            getInstance();
        }
        return (int) (mDisplayUtil.mScaleFactor * dpValue + 0.5f);
    }

}
