package com.xts.shop.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by xts on 2018/10/23.
 * 硬盘缓存工具类    DiskLruCache   ---    LinkedHashMap<String, Entry>     ---   保存位置：SD卡
 */

public class DiskCacheUtil {
    private static final String TAG = "DiskCacheUtil";
    private static int MAX_SIZE = 20*1024*1024;//20M
    private DiskLruCache mDiskLruCache;

    /**
     * 获取缓存路径,初始化DiskLruCache
     * @param context
     */
    public DiskCacheUtil(Context context){
        //获取缓存路径
        String path = getPath(context);
        File file = new File(path);
        if (!file.exists()){
            boolean mkdirs = file.mkdirs();
            boolean is =mkdirs;
            if (is){
                Log.d(TAG, "DiskCacheUtil: "+mkdirs);
            }
        }

        try {
            //创建DiskLruCache对象
            mDiskLruCache = DiskLruCache.open(file, 1, 1, MAX_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath(Context context) {
        String path = "";
        if(Environment.getExternalStorageState() .equals(Environment.MEDIA_MOUNTED) ){
            //外部存储卡,/storage/emulated/0/myCache
           path =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/myCache";
        }else {
            //私有目录 /data/data/package_name/cache
            path = context.getCacheDir().getAbsolutePath();
        }
        Log.d(TAG, "getPath: "+path);
        return path;
    }

    /**
     * 2.写入缓存
     * @param url
     * @param inputStream
     */
    public void writeInputStream2Sd(String url, InputStream inputStream) {
        if (mDiskLruCache != null){
            try {
                //获取Editor对象
                DiskLruCache.Editor edit = mDiskLruCache.edit(StringUtil.hashKeyForDisk(url));
                if (edit != null){
                    //获取editor对象的输出流
                    OutputStream outputStream = edit.newOutputStream(0);
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                    int b;
                    while ((b=bis.read()) != -1){
                        //将输入流写入editor的输出流中
                        bos.write(b);
                    }
                    bis.close();
                    bos.close();

                    Log.d(TAG, "缓存到sd卡: "+url);
                    //提交,数据会写入对应sd目录的文件夹中
                    edit.commit();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 2.写入缓存
     * @param key
     * @param content
     */
    public void writeString2Sd(String key, String content) {
        if (mDiskLruCache != null){
            try {
                //获取Editor对象
                DiskLruCache.Editor edit = mDiskLruCache.edit(StringUtil.hashKeyForDisk(key));
                if (edit != null){
                    //获取editor对象的输出流
                    OutputStream outputStream = edit.newOutputStream(0);

                    BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()));
                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                    int b;
                    while ((b=bis.read()) != -1){
                        //将输入流写入editor的输出流中
                        bos.write(b);
                    }
                    bis.close();
                    bos.close();

                    Log.d(TAG, "缓存到sd卡: "+key);
                    //提交,数据会写入对应sd目录的文件夹中
                    edit.commit();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 3.获取sd卡中的图片
     * @param url
     * @return
     */
    public String getValue(String url){
        if (mDiskLruCache != null){
            try {
                //获取Snapshot对象
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(StringUtil.hashKeyForDisk(url));
                if (snapshot != null){
                    String content = snapshot.getString(0);
                    Log.d(TAG, "来自sd卡的: "+content);
                    return content;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
