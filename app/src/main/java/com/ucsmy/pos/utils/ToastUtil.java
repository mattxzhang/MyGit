package com.ucsmy.pos.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by ucs_zhangjiaheng on 2017/3/1.
 */

public class ToastUtil {
    private static Object object = new Object();

    private static Toast mToast = null;
    public static long showSameToastTime = 0;
    public static String lastToastMsg = "";


    public static Toast getmToast() {
        return mToast;
    }

    public static void showLongToast(Context c, String message) {
        showToast(c, message, Toast.LENGTH_LONG);
    }

    public static void showLongToast(Context c, int resId) {
        showToast(c, c.getString(resId), Toast.LENGTH_LONG);
    }

    public static void showShortToast(Context c, String message) {
        showToast(c, message, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context c, int resId) {
        showToast(c, c.getString(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 自定义显示位置的toast
     *
     * @param c
     * @param msg
     * @param duration
     * @param gravity
     */
    public static void showToast(Context c, String msg, int duration,
                                 int gravity) {
        synchronized (object) {
            if (mToast != null) {
                mToast.setText(msg);
                mToast.setDuration(duration);
            } else {
                mToast = Toast.makeText(c, msg, duration);
            }
            mToast.setGravity(gravity, 0, 0);
            mToast.show();
        }
    }

    public static void showToast(Context c, String msg, int duration) {
        if (c == null) {
            return;
        }
        if (StringUtil.isEmpty(msg)) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {// 非UI主线程
            return;
        }
        synchronized (object) {
            long currentShowTime = System.currentTimeMillis();
            if (!msg.equals(lastToastMsg) ||
                    (currentShowTime - showSameToastTime) > 1800) {
                showSameToastTime = currentShowTime;
                lastToastMsg = msg;
                if (mToast != null &&
                        mToast.getView() != null &&
                        mToast.getView().getParent() == null) {
                    mToast.setText(msg);
                    mToast.setDuration(duration);
                } else {
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(c.getApplicationContext(), msg, duration);
                }
                mToast.show();
            }
        }
    }

}
