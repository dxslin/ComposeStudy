package com.slin.core.logger

import com.slin.compose.study.BuildConfig
import com.slin.compose.study.config.Constants
import timber.log.Timber


/**
 * author: slin
 * date: 2020/9/4
 * description: 日志类，使用Timber打印日志
 *
 */

typealias Supplier<T> = () -> T

fun initLogger(isDebug: Boolean = BuildConfig.DEBUG) {
    if (isDebug) {
        Timber.plant(CoreDebugTree())
    } else {
        Timber.plant(CrashReportTree())
    }
    log { "Initialize logger successful, isDebug: $isDebug" }
}

class CoreDebugTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val newTag = "${Constants.GLOBAL_TAG}_$tag"
        super.log(priority, newTag, message, t)
    }
}

inline fun log(supplier: Supplier<String>) = logd(supplier)

inline fun logd(supplier: Supplier<String>) = Timber.d(supplier())

inline fun logi(supplier: Supplier<String>) = Timber.i(supplier())

inline fun logw(supplier: Supplier<String>) = Timber.w(supplier())

inline fun loge(t: Throwable? = null, supplier: Supplier<String>) =
    if (t == null) {
        Timber.e(supplier())
    } else {
        Timber.e(t, supplier())
    }
