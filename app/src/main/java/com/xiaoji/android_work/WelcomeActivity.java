package com.xiaoji.android_work;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.android.RecyclerRefreshActivity;
import com.xiaoji.framework.utils.ScreenUtils;

public class WelcomeActivity extends BaseActivity{

    /**
     * 优化启动速度
     * 冷启动：
     * 1.第一次安装，加载应用并启动
     * 2.移动后显示一个空白的窗口
     * 3.启动/创建应用程序
     *
     * App内部：
     * 1.创建APP对象/Applaication对象
     * 2.启动主线程（Main/UI Thread）
     * 3.创建应用入口/LAUNCHER
     * 4.填充ViewGroup中的View
     * 5.绘制第一屏界面（measure->layout->draw）
     *
     * 优化：
     * 1.试图优化
     *   设置主题透明
     *   设置启动图片
     * 2.代码优化
     *   优化Application
     *   布局优化
     *   不做阻塞UI线程的操作
     *   加载Bitmap/大图
     *   其它的一些占用主线程的操作
     *
     *
     *  检查App Activity的启动时间
     *  1.shell
     *    ActivityManager  ->  adb shell am start -S -W com.xiaoji.android_work/com.xiaoji.android_work.WelcomeActivity
     *    TotalTime: 343   最后一个Activity的启动耗时
     *    WaitTime: 343    启动一连串Activity的总耗时
     *    Complete   应用创建的时间+TotalTime
     *
     *   2.Log
     *    Android 4.4 开始 ActivityManager增加了Log TAG=displayed
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScreenUtils.setFullScreen(this);
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_welcome);

        fixMargin();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        },1500);
    }

    /**
     * 修改图片边距
     */
    private void fixMargin() {
        View img=findViewById(R.id.img);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) findViewById(R.id.img).getLayoutParams();
        params.topMargin=params.topMargin-(ScreenUtils.getStatusBarHeight(this)/2);
        img.setLayoutParams(params);
    }

}
