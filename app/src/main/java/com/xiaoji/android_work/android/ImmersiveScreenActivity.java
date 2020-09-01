package com.xiaoji.android_work.android;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;
import com.xiaoji.framework.utils.ScreenUtils;

public class ImmersiveScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersive_screen);
        ScreenUtils.immersive(this);
        setHeightAndPadding(this,findViewById(R.id.toolbar));

        /**
         * 全屏显示
         *
         *  requestWindowFeature(Window.FEATURE_NO_TITLE);
         *  Window window=getWindow();
         *  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         *  setContentView();
         *
         * 设置主题
         * android:theme="@android:style/Theme.Black.NoTitleBar"
         * android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen"
         *
         * 或者设置主题属性
         * <style name="AppTheme" parent="AppBaseTheme">
         * <item name="android:windowNoTitle">true</item><!-- 没有标题 -->
         * </style>
         */

        /**
         * 沉浸式状态栏
         * 1.android4.4
         *  <!--沉浸式状态栏-->
         *  <item name="android:windowTranslucentStatus">true</item>
         *  <!--沉浸式虚拟导航栏-->
         *  <item name="android:windowTranslucentNavigation">true</item>
         *
         *  //状态栏
         *  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         *  //虚拟导航栏
         *  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
         *
         *
         *  2.android5.0以上的处理方式
         *
         *
         */

    }

    /**
     * 设置高度和间距
     * @param context
     * @param view
     */
    public void setHeightAndPadding(Context context,View view){
        int statusBarHeight= ScreenUtils.getStatusBarHeight(context);
        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
        layoutParams.height +=statusBarHeight;
        view.setPadding(view.getPaddingLeft(),view.getPaddingTop()+statusBarHeight,view.getPaddingRight(),view.getPaddingBottom());
    }

}
