package com.xiaoji.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ScreenUtils {

    /**
     * 设置沉浸式
     * @param activity
     */
    public static void immersive(Activity activity) {

        //android4.4 不支持沉浸式
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        //android5.0 及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //清除标识
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加标识
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            int visibility=window.getDecorView().getSystemUiVisibility();
            //布局内容全屏显示
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //隐藏虚拟导航栏
            visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            //防止内容区域大小发生变化
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(visibility);
        }else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int resId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if(resId>0){
            return context.getResources().getDimensionPixelSize(resId);
        }
        return  0;
    }

    /**
     * 设置全屏
     * @param activity
     */
    public static void setFullScreen(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=activity.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    public static int dpToPx(Context context, float dpValue) {//dp转换为px
        float scale = context.getResources().getDisplayMetrics().density;//获得当前屏幕密度
        return (int) (dpValue * scale + 0.5f);
    }

}
