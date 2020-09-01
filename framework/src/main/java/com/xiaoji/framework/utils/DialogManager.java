package com.xiaoji.framework.utils;

import android.content.Context;
import android.view.Gravity;

import com.xiaoji.framework.R;
import com.xiaoji.framework.view.DialogView;

public class DialogManager {

    private static volatile DialogManager mInstance=null;
    private DialogManager(){

    }
    public static DialogManager getInstance(){
        if (mInstance==null){
            synchronized (DialogManager.class){
                if (mInstance==null){
                    mInstance=new DialogManager();
                }
            }
        }
        return mInstance;
    }

    public DialogView initView(Context mContext, int layout, int gravity) {
        return new DialogView(mContext,layout, R.style.Theme_Dialog,gravity);
    }

    public DialogView initView(Context mContext,int layout) {
        return new DialogView(mContext,layout, R.style.Theme_Dialog, Gravity.CENTER);
    }

    public void show(DialogView dialogView){
        if (dialogView!=null){
            if (!dialogView.isShowing()){
                dialogView.show();
            }
        }
    }

    public void hide(DialogView dialogView){
        if (dialogView!=null){
            if (dialogView.isShowing()){
                dialogView.dismiss();
            }
        }
    }
}
