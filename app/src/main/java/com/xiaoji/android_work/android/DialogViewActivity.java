package com.xiaoji.android_work.android;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;
import com.xiaoji.framework.utils.DialogManager;
import com.xiaoji.framework.view.DialogView;
import com.xiaoji.framework.view.LoadingView;

public class DialogViewActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                DialogView dialogView= DialogManager.getInstance().initView(DialogViewActivity.this, R.layout.dialog_view);
//                dialogView.show();
                LoadingView loadingView=new LoadingView(DialogViewActivity.this);
                loadingView.show();

            }
        },1000);
    }
}
