package com.slin.splayandroid.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * author: slin
 * date: 2021/8/19
 * description:
 *
 */

fun Context.findOwnerActivity(): Activity? {
    var context = this
    var activity: Activity? = null
    while (context is ContextWrapper) {
        if (context is Activity) {
            activity = context
            break
        } else {
            context = context.baseContext
        }
    }
    return activity
}

/**
 * 设置全屏
 */
fun Context.setFullScreen(fullScreen: Boolean) {
    val activity = findOwnerActivity()
    check(activity != null) { "Context必须拥有宿主Activity" }

    val window = activity.window
    val controller = WindowCompat.getInsetsController(window, window.decorView)

    controller?.apply {
        val type = WindowInsetsCompat.Type.systemBars()

        if (fullScreen) {
            hide(type)
        } else {
            show(type)
        }

    }
}