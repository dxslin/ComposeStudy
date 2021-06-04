package com.slin.compose.study.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Yellow200 = Color(0xffffeb46)
val Yellow500 = Color(0xffffde03)
val Yellow700 = Color(0xffffc000)

val Blue200 = Color(0xff6ec6ff)
val Blue500 = Color(0xff2196f3)
val Blue700 = Color(0xff0069c0)

val Red200 = Color(0xffff7961)
val Red500 = Color(0xfff44336)
val Red700 = Color(0xffba000d)

val Pink200 = Color(0xffff6090)
val Pink500 = Color(0xffe91e63)
val Pink700 = Color(0xffb0003a)



/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}
