package com.xiaoji.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoji.framework.helper.FileHelper;

public class BitmapUtils {

    public static Bitmap getViewBitmap(Context mContext, ViewGroup viewGroup){
        /**
         * setDrawingCacheEnabled
         * 保留我们的绘制副本
         * 1.重新测量
         * 2.重新布局
         * 3.得到我们的DrawingCache
         * 4.转换成Bitmap
         */
        viewGroup.setDrawingCacheEnabled(true);

        viewGroup.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        viewGroup.layout(0, 0, viewGroup.getMeasuredWidth(),
                viewGroup.getMeasuredHeight());

        Bitmap mBitmap = viewGroup.getDrawingCache();

        if (mBitmap != null) {
            FileHelper.getInstance().saveBitmapToAlbum(mContext, mBitmap);
        }
        return mBitmap;
    }

}
