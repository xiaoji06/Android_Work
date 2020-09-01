package com.xiaoji.framework.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.xiaoji.framework.R;
import com.xiaoji.framework.utils.AnimUtils;
import com.xiaoji.framework.utils.DialogManager;

public class LoadingView {

    private DialogView mLoadingView;
    private View loading;
    private TextView loading_text;
    private ObjectAnimator animation;

    public LoadingView(Context mContext){
        mLoadingView= DialogManager.getInstance().initView(mContext, R.layout.dialog_loading);
        loading=mLoadingView.findViewById(R.id.loading);
        loading_text=mLoadingView.findViewById(R.id.loading_text);
        animation= AnimUtils.rotation(loading);
    }

    public void setLoadingText(String text){
        if (!TextUtils.isEmpty(text)){
            loading_text.setText(text);
        }
    }

    public void show(){
        animation.start();
        DialogManager.getInstance().show(mLoadingView);
    }

    public void show(String text){
        setLoadingText(text);
        animation.start();
        DialogManager.getInstance().show(mLoadingView);
    }

    public void hide(){
        animation.cancel();
        DialogManager.getInstance().show(mLoadingView);
    }

    /**
     * 外部是否可以点击消失
     *
     * @param flag
     */
    public void setCancelable(boolean flag) {
        mLoadingView.setCancelable(flag);
    }

}
