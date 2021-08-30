package com.slin.splayandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.google.accompanist.insets.ProvideWindowInsets
import com.slin.core.ui.CoreFragment
import com.slin.splayandroid.ui.theme.AppTheme

/**
 * author: slin
 * date: 2021/8/24
 * description:
 *
 */
abstract class ComposeFragment : CoreFragment() {

    protected var composeView: ComposeView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            composeView = this
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            fitsSystemWindows = false
            setContent {
                AppTheme {
                    ProvideWindowInsets {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colors.background) {
                            this@ComposeFragment.Content()
                        }
                    }
                }
            }
        }
    }

    @Composable
    protected abstract fun ComposeFragment.Content()

}