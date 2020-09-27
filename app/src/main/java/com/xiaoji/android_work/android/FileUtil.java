package com.xiaoji.android_work.android;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Created by liuwenxing on 2017/8/1.
 */

public class FileUtil {
     /**
        * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean tag = true;
        InputStream inStream = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {                  //文件存在时
                inStream = new FileInputStream(oldPath);      //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;            //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }

            }
        }  catch (Exception e) {
            tag = false;
            e.printStackTrace();
        }finally {
            if(inStream!=null){
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  tag;
    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            (new File(newPath)).mkdirs();       //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+ File.separator+file[i]);
                }


                if(temp.isFile()){
                    input = new FileInputStream(temp);
                    output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                }
                if(temp.isDirectory()){           //如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }  catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }finally {
            try {
                if(output!=null){
                    output.close();
                }
                if(input!=null){
                    input.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    /**
     * 检查剩余SD空间 B为单位
     * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public  static long residueSDCardSize() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSizeLong();
            long blockCount = sf.getBlockCountLong();
            long availCount = sf.getAvailableBlocksLong();
            return availCount*blockSize;
        }
        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public  static long residueSystemSize() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSizeLong();
        long blockCount = sf.getBlockCountLong();
        long availCount = sf.getAvailableBlocksLong();
        return availCount*blockSize;
    }


    /**
     * 按行读取txt
     *
     * @throws Exception
     */
    public static String readText(Context context, int rawid) throws Exception {
        InputStream is = context.getResources().openRawResource(rawid);
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        return buffer.toString();
    }

    public static boolean renameFile(String oldPath, String newPath){
        File toBeRenamed = new File(oldPath);
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            return  false;
        }

        File newFile = new File(newPath);

        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            return true;
        } else {
            return false;
        }
    }
    public static String getTempDirectoryPath(Context context) {
        File cache = null;

        // SD Card Mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache = context.getExternalCacheDir();
            if(cache==null){
                cache = context.getCacheDir();
            }
        }
        // Use internal storage
        else {
            cache = context.getCacheDir();
        }

        if(cache!=null){
            // Create the cache directory if it doesn't exist
            cache.mkdirs();
            return cache.getAbsolutePath();
        }
        return "";
    }

    public static String getSDTempDirectoryPath(Context context) {
        File cache = null;

        // SD Card Mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache = Environment.getExternalStorageDirectory();
            if(cache==null){
                cache = context.getCacheDir();
            }
        }
        // Use internal storage
        else {
            cache = context.getCacheDir();
        }

        if(cache!=null){
            // Create the cache directory if it doesn't exist
            cache.mkdirs();
            return cache.getAbsolutePath();
        }
        return "";
    }

    public static String getFileDirectoryPath(Context context, @Nullable String type) {
        File cache = null;
        // SD Card Mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache = context.getExternalFilesDir(type);
        }
        // Use internal storage
        else {
            cache = context.getFilesDir();
        }

        // Create the cache directory if it doesn't exist
        cache.mkdirs();
        return cache.getAbsolutePath();
    }


    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
