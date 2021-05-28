package com.slin.compose.study.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.set

/**
 * author: slin
 * date: 2021/5/28
 * description:
 *
 */

fun ImageBitmap.gray(): ImageBitmap {
    if (this.colorSpace.isSrgb) {
        val colors = IntArray(width * height * 4)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        var color: Int
        val lineCount = width * 4
        for (i in colors.indices step 4) {
            val avg = (colors[i] + colors[i + 1] + colors[i + 2]) / 4
            color = colors[i + 3].shl(24).or(avg.shl(16)).or(avg.shl(8)).or(avg)

            bitmap.set(i % lineCount / 4, i / lineCount, color = color)
        }
        return bitmap.asImageBitmap()
    } else {
        throw UnsupportedOperationException("不支持该类型的图片")
    }
}