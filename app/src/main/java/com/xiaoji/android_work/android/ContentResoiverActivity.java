package com.xiaoji.android_work.android;

import android.content.ContentResolver;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.BaseActivity;

public class ContentResoiverActivity extends BaseActivity {

    ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentResolver=getContentResolver();
    }
}
