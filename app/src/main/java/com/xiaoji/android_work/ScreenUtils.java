package com.xiaoji.android_work;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.mmkv.MMKV;

public class ScreenUtils {

    public static String SCREEN_LONGLIGHT_KEY = "SCREEN_LONGLIGHT_KEY";

    /**
     * 保持屏幕常亮
     * @param activity
     */
    public static void keepScreenLongLight(Activity activity){
        boolean isOpentLight = MMKV.defaultMMKV().decodeBool(SCREEN_LONGLIGHT_KEY,false);
        Window window = activity.getWindow();
        if (isOpentLight){
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}
