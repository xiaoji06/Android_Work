package com.xiaoji.android_work;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mmkv.MMKV;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        LanguageUtils.setActivityConfiguration(newBase, MMKV.defaultMMKV().decodeInt(LanguageUtils.LANGUAGE_KEY));
    }
}
