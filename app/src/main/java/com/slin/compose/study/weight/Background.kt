package com.slin.compose.study.weight

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

/**
 * author: slin
 * date: 2021/8/20
 * description:
 *
 */
fun Modifier.background(@DrawableRes id: Int): Modifier =
    composed(inspectorInfo = debugInspectorInfo {
        name = "background"
        properties["backgroundResId"] = id
    }) {
        val context = LocalContext.current
        Background(context, id)
    }

private class Background(private val drawable: Drawable) : DrawModifier {

    private var bitmap: ImageBitmap? = null

    constructor(context: Context, @DrawableRes id: Int) : this(
        ContextCompat.getDrawable(context, id)!!
    )

    override fun ContentDrawScope.draw() {

        if (bitmap == null) {
            bitmap = drawable.toBitmap(size.width.toInt(), size.height.toInt()).asImageBitmap()
        }
        drawImage(bitmap!!)

        drawContent()
    }

}