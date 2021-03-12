package com.qnotifiedx.app.util

import android.util.Log

object Log {
    const val TAG = "QNotifiedX"

    /**
     * 打印日志 等级:Info
     * @param msg 消息
     */
    fun i(msg: String) {
        Log.i(TAG, msg)
    }

    /**
     * 打印日志 等级:Debug
     * @param msg 消息
     */
    fun d(msg: String) {
        Log.d(TAG, msg)
    }

    /**
     * 打印日志 等级:Warn
     * @param msg 消息
     */
    fun w(msg: String) {
        Log.w(TAG, msg)
    }

    /**
     * 打印日志 等级:Error
     * @param e 异常
     * @param msg 消息
     */
    fun e(e: Exception, msg: String = "") {
        if (msg.isEmpty())
            Log.e(TAG, e.toString())
        else
            Log.e(TAG, "$msg : $e")
    }

    /**
     * 打印日志 等级:Error
     * @param e 错误
     * @param msg 消息
     */
    fun e(e: Error, msg: String = "") {
        if (msg.isEmpty())
            Log.e(TAG, e.toString())
        else
            Log.e(TAG, "$msg : $e")
    }

    /**
     * 打印日志 等级:Error
     * @param thr Throwable
     * @param msg 消息
     */
    fun t(thr: Throwable, msg: String = "") {
        if (msg.isEmpty())
            Log.e(TAG, thr.toString())
        else
            Log.e(TAG, "$msg : $thr")
    }
}
