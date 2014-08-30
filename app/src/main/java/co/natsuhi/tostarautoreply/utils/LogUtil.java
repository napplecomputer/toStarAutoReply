package co.natsuhi.tostarautoreply.utils;

import android.util.Log;

import co.natsuhi.tostarautoreply.BuildConfig;

public class LogUtil {
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }
}
