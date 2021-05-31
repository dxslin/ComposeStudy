package com.slin.compose.study.utils


import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.core.graphics.set
import com.slin.core.logger.logd
import kotlin.math.roundToInt

/**
 * author: slin
 * date: 2021/5/28
 * description:
 *
 */

/**
 * 将图片转化为灰阶图
 */
fun ImageBitmap.gray(): ImageBitmap {
    logd { "gray width = $width height = $height" }
    if (this.config == ImageBitmapConfig.Argb8888) {
        val colors = IntArray(width * height)
        readPixels(colors)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (i in colors.indices) {
            val org = colors[i]
            val alpha = org.and(0xFF000000.toInt())
            val red = org.and(0x00FF0000).shr(16)
            val green = org.and(0x0000FF00).shr(8)
            val blue = org.and(0x000000FF)

            //用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
            val gray = (red * 0.3f + green * 0.59f + blue * 0.11f).roundToInt()
            val newColor = alpha.or(gray.shl(16)).or(gray.shl(8).or(gray))
            bitmap.set(i % width, i / width, color = newColor)
        }
        return bitmap.asImageBitmap()
    } else {
        throw UnsupportedOperationException("不支持该类型的图片")
    }
}

/**
 * 将图片转化为灰阶图
 */
fun ImageBitmap.gray2(): ImageBitmap {
    logd { "gray2 width = $width height = $height" }
    /**
     *  [ a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t ] When applied to a color [r, g, b, a],
     *  the resulting color is computed as (after clamping)
     *  R' = a*R + b*G + c*B + d*A + e; G' = f*R + g*G + h*B + i*A + j;
     *  B' = k*R + l*G + m*B + n*A + o; A' = p*R + q*G + r*B + s*A + t;
     */
    val colorMatrix = ColorMatrix()
    /**
     * Set the matrix to affect the saturation(饱和度) of colors.
     * A value of 0 maps the color to gray-scale（灰阶）. 1 is identity
     */
    colorMatrix.setToSaturation(0.00f)
    val filter = ColorFilter.colorMatrix(colorMatrix)

    val paint = Paint()
    paint.colorFilter = filter

    val imageBitmap = ImageBitmap(width = width, height = height, ImageBitmapConfig.Argb8888)
    //这里一定要将density赋值成一样的，否则灰度图可能跟原图显示区域不一样
    imageBitmap.asAndroidBitmap().density = asAndroidBitmap().density

    val canvas = Canvas(imageBitmap)
    canvas.drawImage(this, Offset.Zero, paint)
    return imageBitmap
}