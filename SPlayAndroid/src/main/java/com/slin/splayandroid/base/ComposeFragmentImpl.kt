package com.slin.splayandroid.base

import androidx.compose.runtime.Composable

/**
 * author: slin
 * date: 2021/8/26
 * description:
 *
 */
class ComposeFragmentImpl(
    private val content: @Composable ComposeFragment.() -> Unit
) : ComposeFragment() {

    @Composable
    override fun ComposeFragment.Content() {
        content()
    }

}