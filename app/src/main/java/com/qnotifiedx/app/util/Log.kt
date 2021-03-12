package com.qnotifiedx.app.util

import de.robv.android.xposed.XposedBridge

object Log {
    const val TAG = "QNotifiedX"

    /**
     * 打印日志 等级:Info
     * @param msg 消息
     */
    fun i(msg: String) {
        XposedBridge.log("$TAG INFO : $msg")
    }

    /**
     * 打印日志 等级:Debug
     * @param msg 消息
     */
    fun d(msg: String) {
        XposedBridge.log("$TAG DEBUG : $msg")
    }

    /**
     * 打印日志 等级:Warn
     * @param msg 消息
     */
    fun w(msg: String) {
        XposedBridge.log("$TAG WARN : $msg")
    }

    /**
     * 打印日志 等级:Error
     * @param e 异常
     * @param msg 消息
     */
    fun e(e: Exception, msg: String = "") {
        if (msg.isEmpty())
            XposedBridge.log("$TAG EXCEPTION : $e")
        else
            XposedBridge.log("$TAG EXCEPTION : $msg \n${e}")
    }

    /**
     * 打印日志 等级:Error
     * @param e 错误
     * @param msg 消息
     */
    fun e(e: Error, msg: String = "") {
        if (msg.isEmpty())
            XposedBridge.log("$TAG ERROR : $e")
        else
            XposedBridge.log("$TAG ERROR : $msg \n${e}")
    }

    /**
     * 打印日志 等级:Throwable
     * @param thr Throwable
     * @param msg 消息
     */
    fun t(thr: Throwable, msg: String = "") {
        if (msg.isEmpty())
            XposedBridge.log("$TAG THROW : $thr")
        else
            XposedBridge.log("$TAG THROW : $msg \n${thr}")
    }
}
