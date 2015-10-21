package org.social.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by caowenhua on 2015/10/18.
 */
public class FileUtils {
    public static String setImageCacheDir(Context context) {
        String filePath;
        if (isSDCardExist()) {
            String dir = Environment.getExternalStorageDirectory().getPath()
                    + "/w";
            File dirFile = new File(dir);
            if (!dirFile.exists() && !dirFile.mkdir()) {
            }

            String imgDir = Environment.getExternalStorageDirectory().getPath()
                    + "/w/imagecache";
            File imgFile = new File(imgDir);
            if (!imgFile.exists() && !imgFile.mkdir()) {
            }

            filePath = Environment.getExternalStorageDirectory().getPath()
                    + "/w/imagecache/";
        } else {
            String dir = context.getCacheDir().getAbsolutePath() + "/imagecache";
            File imgFile = new File(dir);
            if (!imgFile.exists() && !imgFile.mkdir()) {
            }
            filePath = context.getCacheDir().getAbsolutePath() + "/imagecache/";
        }
        File file = new File(filePath);
        if (!file.exists()) {
            boolean b = file.mkdirs();
        }
        return filePath + System.currentTimeMillis() + "jpg";
    }

    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getFilePathByURI(Activity activity, Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        return path;
    }
}
