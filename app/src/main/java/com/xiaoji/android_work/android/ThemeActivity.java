package com.xiaoji.android_work.android;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

public class ThemeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
    }

    /**
     * Material Design 主题  API21以上使用
     * 1.android:style/Theme.Meterial
     * 2.android:style/Theme.Meterial.Light
     * 3.android:style/Theme.Meterial.Light.DarkActionBar
     *
     * Material Design 兼容主题
     * 1.Theme.AppCompat.Light
     * 2.Theme.AppCompat.Light.DarkActionBar
     *
     * colorPrimary  标题栏颜色
     * colorPrimaryDark 状态栏颜色
     * colorAccent  强调色
     * textColorPrimary  标题栏上字体和图标的颜色
     * windowBackground  窗口背景色
     * navigationBarColor  虚拟导航栏颜色
     */


}
