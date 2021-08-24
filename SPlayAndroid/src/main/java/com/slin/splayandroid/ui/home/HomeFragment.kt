package com.slin.splayandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import com.google.accompanist.insets.ProvideWindowInsets
import com.slin.splayandroid.base.BaseFragment
import com.slin.splayandroid.ui.theme.AppTheme

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */
class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            fitsSystemWindows = true
            setContent {
                AppTheme {
                    ProvideWindowInsets {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colors.background) {
                            HomePage()
                        }
                    }
                }
            }

        }
    }

}