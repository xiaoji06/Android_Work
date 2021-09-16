package com.xiaoji.android_work;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.hjq.toast.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;

import java.lang.reflect.Method;

import cn.like.nightmodel.NightModelManager;

import static android.os.Build.VERSION.SDK_INT;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MMKV.initialize(base);
        //保存原始系统的语言
        LanguageUtils.systemLocale = LanguageUtils.getSysLocale();
        LanguageUtils.setApplicationConfiguration(base,MMKV.defaultMMKV().decodeInt(LanguageUtils.LANGUAGE_KEY));
        MultiDex.install(this);
        HideApi();
    }

    void HideApi() {
        try {
            if (SDK_INT >= Build.VERSION_CODES.P) {
                Class<?> VMRuntimeClass = Class.forName("dalvik.system.VMRuntime");
                Method getRuntimeMethod = VMRuntimeClass.getDeclaredMethod("getRuntime");
                Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
                Method setHiddenApiExemptionsMethod = (Method) getDeclaredMethod.invoke(VMRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
                Object runtimeObject = getRuntimeMethod.invoke(null);
                if (setHiddenApiExemptionsMethod != null && runtimeObject != null) {
                    setHiddenApiExemptionsMethod.invoke(runtimeObject, new Object[]{new String[]{"L"}});
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(App.this);
        DoraemonKit.install(this);
        //子线程初始化第三方组件
        /**
         * 1.必要的组件在程序主页去初始化
         * 2.如果一定要在App中初始化，要尽可能的延迟
         * 3.非必要的组件。在子线程中优化
         */
       // AppCompatDelegate.setDefaultNightMode(MMKV.defaultMMKV().decodeBool(Contants.LIGHT_MODE_KEY)?AppCompatDelegate.MODE_NIGHT_YES:AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        NightModelManager.getInstance().init(this);
        //开启子线程加载第三方
        new Thread(new Runnable() {
            @Override
            public void run() {
                //设置线程的优先级，不与主线程抢资源
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                //初始化Bugly
                CrashReport.initCrashReport(getApplicationContext(), "98f1c54818", false);
                try {
                    Thread.sleep(5000);//建议延迟初始化，可以发现是否影响其它功能，或者是崩溃！
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                //保持屏幕常亮
                ScreenUtils.keepScreenLongLight(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存原始系统的语言
        LanguageUtils.systemLocale = LanguageUtils.getSysLocale(newConfig);
        LanguageUtils.setApplicationConfiguration(getApplicationContext(),MMKV.defaultMMKV().decodeInt(LanguageUtils.LANGUAGE_KEY));
    }
}
