package com.xiaoji.android_work.android;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;

@RuntimePermissions
public class PhotoActivity extends BaseActivity {

    public static int CAPTURE_PHOTO_REQUEST_CODE=0x12;
    public static int REQUEST_PICKER_AND_CROP=0x13;
    public static int REQUEST_PICK_IMAGE=0x14;
    private File photoDir;
    private File photoFile;
    private Uri fileUri;

    private String storagePath = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath() +File.separator+"000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        findViewById(R.id.takePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoActivityPermissionsDispatcher.takePhotoWithPermissionCheck(PhotoActivity.this);
            }
        });

        findViewById(R.id.getPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });
    }

    /**
     * 拍照
     */
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void takePhoto() {
        photoDir =new File(storagePath);
        Log.e("path",storagePath);
        if (!photoDir.exists()) {
            Log.d("jim", "path1 create:" + photoDir.mkdirs());
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        photoFile = new File(photoDir,timeStamp+".jpg");
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(this, this.getPackageName() +".provider", photoFile);
        }else {
            fileUri = Uri.fromFile(photoFile);
        }
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(captureIntent, CAPTURE_PHOTO_REQUEST_CODE);
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showRationaleForCamera(PermissionRequest request) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("提示：");
        dialog.setMessage("是否授予权限");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.proceed();
            }
        });
        dialog.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        });
        dialog.create().show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void onCameraDenied() {
        Toast.makeText(this, "拒绝", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void onCameraNeverAskAgain() {
        Toast.makeText(this, "不再显示", Toast.LENGTH_SHORT).show();
        startAppSetting(this,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PhotoActivityPermissionsDispatcher.onRequestPermissionsResult(PhotoActivity.this, requestCode, grantResults);
    }

    /**
     * 前往设置中心
     *
     * @param activity 上下文
     * @param requestCode 请求码 用来传给startActivityForResult作为回调标识符，不需要回调处理的不用设置，跳转直接用startActivity即可
     */
    public void startAppSetting(Activity activity, int requestCode) {
        try {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            if(android.os.Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.putExtra(EXTRA_APP_PACKAGE, activity.getPackageName());
                intent.putExtra(EXTRA_CHANNEL_ID, activity.getApplicationInfo().uid);
                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", activity.getPackageName());
                intent.putExtra("app_uid", activity.getApplicationInfo().uid);
            } else {
                //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                    intent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                }
            }
            activity.startActivityForResult(intent,requestCode);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            Intent intent = new Intent();
            //下面这种方案是直接跳转到当前应用的设置界面。
            //https://blog.csdn.net/ysy950803/article/details/71910806
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent,requestCode);
        }
    }

    /**
     * 相册
     */
    private void getPhoto(){
        photoDir =new File(storagePath);
        Log.e("path",storagePath);
        if (!photoDir.exists()) {
            Log.d("jim", "path1 create:" + photoDir.mkdirs());
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        photoFile = new File(photoDir,timeStamp+".jpg");
        if (!photoFile.exists()) {
            try {
                photoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fileUri = Uri.fromFile(photoFile);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);

    }

    //拍照完成后 获取目标文件 跳转到裁剪页面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_PICKER_AND_CROP){
            if (resultCode == RESULT_OK) {
                ((ImageView)findViewById(R.id.img)).setImageBitmap(
                        BitmapFactory.decodeFile(photoFile.getAbsolutePath())
                );
            }
        }


        if (requestCode == CAPTURE_PHOTO_REQUEST_CODE || requestCode ==REQUEST_PICK_IMAGE) {
            //获取拍照后图片路径
            if (photoFile != null && fileUri !=null) {
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    //需要加上这两句话 ： uri 权限
                    if (requestCode ==REQUEST_PICK_IMAGE){
                        intent.setDataAndType(data.getData(), "image/*");
                    }else {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intent.setDataAndType(fileUri, "image/*");
                    }
                    //intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 300);
                    intent.putExtra("outputY", 300);
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", false);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    intent.putExtra("noFaceDetection", true); // no face detection
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    intent = Intent.createChooser(intent, "裁剪图片");
                    startActivityForResult(intent, REQUEST_PICKER_AND_CROP);
                } else {
                    if (photoFile.exists()) {
                        photoFile.delete();
                    }
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}