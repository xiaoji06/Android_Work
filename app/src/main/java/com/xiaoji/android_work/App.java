package com.xiaoji.android_work;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 1.必要的组件在程序主页去初始化
         * 2.如果一定要在App中初始化，要尽可能的延迟
         * 3.非必要的组件。在子线程中优化
         */

        //开启子线程加载第三方
        new Thread(new Runnable() {
            @Override
            public void run() {
                //设置线程的优先级，不与主线程抢资源
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                //子线程初始化第三方组件
                MMKV.initialize(App.this);
                //初始化Bugly
                CrashReport.initCrashReport(getApplicationContext(), "98f1c54818", false);
                try {
                    Thread.sleep(5000);//建议延迟初始化，可以发现是否影响其它功能，或者是崩溃！
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //主线程延迟加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 2000);

    }

}
