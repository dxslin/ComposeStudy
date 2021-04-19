package com.slin.compose.study.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp


/**
 * author: slin
 * date: 2021/3/12
 * description:
 *
 */

object Size {
    /**  default 4.dp */
    val mini = 4.dp
    /** default 8.dp */
    val small = 8.dp
    /** default 16.dp */
    val medium = 16.dp
    /** default 24.dp */
    val large = 24.dp
}

class Paddings(
    mini: PaddingValues,
    small: PaddingValues,
    medium: PaddingValues,
    large: PaddingValues,
) {
    /**  default 4.dp */
    var mini by mutableStateOf(mini, structuralEqualityPolicy())
        internal set

    /** default 8.dp */
    var small by mutableStateOf(small, structuralEqualityPolicy())
        internal set

    /** default 16.dp */
    var medium by mutableStateOf(medium, structuralEqualityPolicy())
        internal set

    /** default 24.dp */
    var large by mutableStateOf(large, structuralEqualityPolicy())
        internal set

    fun copy(
        mini: PaddingValues = this.mini,
        small: PaddingValues = this.small,
        medium: PaddingValues = this.medium,
        large: PaddingValues = this.large
    ) = Paddings(mini, small, medium, large)

}

internal fun defaultPadding(
    mini: PaddingValues = PaddingValues(Size.mini),
    small: PaddingValues = PaddingValues(Size.small),
    medium: PaddingValues = PaddingValues(Size.medium),
    large: PaddingValues = PaddingValues(Size.large)
) = Paddings(mini, small, medium, large)

internal val LocalPaddings = staticCompositionLocalOf { defaultPadding() }
