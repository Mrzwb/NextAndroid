package com.zwb.next.application.systemui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;

/**
 * 全屏活动
 *
 * @author zhouwb 2021-01-21
 */
public class FullScreenActivity extends Activity {

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.hideSystemUI(this.getWindow());
            }
        }
    }

    /**
     * 全屏模式
     *  使用常规的沉浸式模式.
     *  //For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
     *  //Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
     * @param window
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void hideSystemUI(final Window window) {
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * Shows the system bars by removing all the flags
     * except for the ones that make the content appear under the system bars.
     *
     * @param window
     */
    public static void showSystemUI(final Window window) {
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
