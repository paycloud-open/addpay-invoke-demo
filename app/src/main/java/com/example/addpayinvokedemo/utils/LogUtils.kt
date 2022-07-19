package com.example.addpayinvokedemo.utils

import android.util.Log
import com.example.addpayinvokedemo.BuildConfig


object LogUtils {

    private var openLog = BuildConfig.DEBUG

    public fun setLog(tag: String, msg: String) {
        if (openLog) {
            Log.e(tag, ":$msg")
        }
    }

}