package com.xiaoji.android_work;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.tencent.mmkv.MMKV;
import com.xiaoji.android_work.androidstudio.Setting;

import org.greenrobot.eventbus.EventBus;

import cn.like.nightmodel.NightModelManager;

public class SettingActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private TextView current;
    private int sellectMode;
    private int currentMode;

    @Override
    protected void onDestroy() {
        NightModelManager.getInstance().detach(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        NightModelManager.getInstance().attach(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        radioGroup = findViewById(R.id.languageRg);

        currentMode = MMKV.defaultMMKV().decodeInt(LanguageUtils.LANGUAGE_KEY,0);
        radioGroup.check(currentMode == 0 ?R.id.flow:currentMode == 1?R.id.chinese:R.id.english);
        sellectMode = currentMode;

        current = findViewById(R.id.current);
        current.setText(getApplicationContext().getResources().getString(R.string.setting) + LanguageUtils.systemLocale.getLanguage());

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage(sellectMode);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.flow:
                        sellectMode = 0;
                        break;
                    case R.id.chinese:
                        sellectMode = 1;
                        break;
                    case R.id.english:
                        sellectMode = 2;
                        break;
                    default:
                        break;
                }
            }
        });

        ((CheckBox)findViewById(R.id.lightMode)).setChecked(MMKV.defaultMMKV().decodeBool(Contants.LIGHT_MODE_KEY));
        ((CheckBox)findViewById(R.id.lightMode)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    NightModelManager.getInstance().applyNightModel(SettingActivity.this);
                   // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    MMKV.defaultMMKV().encode(Contants.LIGHT_MODE_KEY,true);
                }else {
                    NightModelManager.getInstance().applyDayModel(SettingActivity.this);
                   // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    MMKV.defaultMMKV().encode(Contants.LIGHT_MODE_KEY,false);
                }
            }
        });
        ((CheckBox)findViewById(R.id.longLight)).setChecked(MMKV.defaultMMKV().decodeBool(ScreenUtils.SCREEN_LONGLIGHT_KEY));
        ((CheckBox)findViewById(R.id.longLight)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MMKV.defaultMMKV().encode(ScreenUtils.SCREEN_LONGLIGHT_KEY,true);
                }else {
                    MMKV.defaultMMKV().encode(ScreenUtils.SCREEN_LONGLIGHT_KEY,false);
                }
                ScreenUtils.keepScreenLongLight(SettingActivity.this);
            }
        });

    }


    private void changeLanguage(int locale) {
        LanguageUtils.setLanguageConfiguration(SettingActivity.this,locale);
        MMKV.defaultMMKV().encode(LanguageUtils.LANGUAGE_KEY,locale);
        EventBus.getDefault().post("chongqi");
        finish();
    }
}
