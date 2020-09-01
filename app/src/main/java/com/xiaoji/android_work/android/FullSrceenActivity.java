package com.xiaoji.android_work.android;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.MainActivity;
import com.xiaoji.android_work.R;
import com.xiaoji.framework.utils.ScreenUtils;

public class FullSrceenActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.setFullScreen(this);
        /**
         * 全面屏适配
         *
         *
         * <meta-data
         *         android:name="android.max_aspect"
         *         android:value="2.4" />
         *
         * android:resizeableActivity="true"
         * android:maxAspectRatio="2.4"
         *
         * <!--适配华为（huawei）刘海屏-->
         *     <meta-data
         *         android:name="android.notch_support"
         *         android:value="true"/>
         * <!--适配小米（xiaomi）刘海屏-->
         *     <meta-data
         *         android:name="notch.config"
         *         android:value="portrait|landscape" />
         */

        /**
         * 刘海屏适配
         * 如果非全屏模式（有状态栏），则app不受刘海屏的影响，刘海屏的高度就是状态栏的高度
         * 如果全屏模式，app未适配刘海屏，系统会做特殊处理，竖屏向下移动，横盘向右移动
         * 除特殊情况下一般沉浸式即可
         *
         * 其他手机厂商（华为，小米，oppo，vivo）适配
         * 华为:https://devcenter-test.huawei.com/consumer/cn/devservice/doc/50114
         * 小米:https://dev.mi.com/console/doc/detail?pId=1293
         * Oppo:https://open.oppomobile.com/service/message/detail?id=61876
         * Vivo:https://dev.vivo.com.cn/documentCenter/doc/103
         */

        /**
         * 1.判断手机厂商
         * 2.判断是否有刘海屏
         * 3.是否让内容区域延伸进刘海
         * 4.设置空间是否避开刘海区
         * 5.获取刘海的高度displayCutout.getSafeInsetTop()
         * 6.设置父View的padding
         */

        //判断手机是否是刘海屏
        Window window=getWindow();
        boolean hasDisplayCutout=hasDisplayCutout(window);

        if (hasDisplayCutout){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                //让内容区域延伸近刘海
                WindowManager.LayoutParams params=window.getAttributes();
                /**
                 * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT  全屏模式，内容下移，非全屏不收影响
                 * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES  允许内容延伸到刘海区
                 * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER  不允许内容延伸到刘海区
                 */
                params.layoutInDisplayCutoutMode=WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                window.setAttributes(params);

                ScreenUtils.immersive(this);
            }
        }

        setContentView(R.layout.activity_fullscreen);

        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FullSrceenActivity.this, MainActivity.class));
            }
        });
    }

    public boolean hasDisplayCutout(Window window){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        DisplayCutout displayCutout;
        View rootView =window.getDecorView();
        WindowInsets insets= null;
            insets = rootView.getRootWindowInsets();
            if (insets!=null){
                displayCutout=insets.getDisplayCutout();
                if (displayCutout!=null){
                    if (displayCutout.getBoundingRects()!=null&&displayCutout.getBoundingRects().size()>0&&displayCutout.getSafeInsetTop()>0){
                        return true;
                    }

                }
            }
        }
        return true;
    }
}
