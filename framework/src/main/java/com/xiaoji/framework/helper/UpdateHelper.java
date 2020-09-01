package com.xiaoji.framework.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;


import java.io.File;

public class UpdateHelper {

    private Context mContext;


    public UpdateHelper(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 安装Apk
     *
     * @param filePath
     * @return
     */
    public void installApk(String filePath) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        File apkFile = new File(filePath);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }
}
