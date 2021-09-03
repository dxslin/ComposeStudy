package com.slin.splayandroid.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * author: slin
 * date: 2021/9/3
 * description:
 *
 */
fun Context.toast(duration: Int = Toast.LENGTH_SHORT, msg: () -> CharSequence) {
    Toast.makeText(this, msg(), duration).show()
}

fun Fragment.toast(duration: Int = Toast.LENGTH_SHORT, msg: () -> CharSequence) {
    requireContext().toast(duration, msg)
}