package com.ucsmy.pos.utils;

import android.os.Environment;

import com.ucsmy.pos.FosApp;

import java.io.File;

/**
 * Created by ucs_zhangjiaheng on 2017/3/13.
 */

public class StorageUtil {
    public static boolean isExistSDCard = false;
    private static File mRootDir = null;

    static void init() {
        if (mRootDir == null) {
            isExistSDCard = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);

            mRootDir = Environment.getExternalStorageDirectory();
        }
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static File getAvailableCacheDir() {
        if (isExternalStorageWritable()) {
            return getAvailableExternalStorage();
        } else {
            return FosApp.app.getCacheDir();
        }
    }

    static public File getAvailableExternalStorage() {
        if (mRootDir == null) {
            mRootDir = Environment.getExternalStorageDirectory();
        }
        return mRootDir;
    }

    static public File getAppExternalStorage() {
        if (mRootDir == null) {
            init();
        }
        return new File(mRootDir, FosApp.app.getPackageName());
    }
}
