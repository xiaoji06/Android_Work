package com.xiaoji.framework.view;

import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;

public class DialogView extends AppCompatDialog {
    public DialogView(Context context, int layoutId,int theme,int gravity) {
        super(context, theme);
        setContentView(layoutId);
        Window window=getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity=gravity;
        window.setAttributes(params);
    }
}
