package com.slin.splayandroid.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.insets.ProvideWindowInsets
import com.slin.splayandroid.base.BaseFragment
import com.slin.splayandroid.nav.Screen
import com.slin.splayandroid.nav.navigate
import com.slin.splayandroid.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */
@AndroidEntryPoint
class WelcomeFragment : BaseFragment() {

    private val viewModel: WelcomeViewModel by viewModels()


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
            setContent {
                AppTheme {
                    ProvideWindowInsets {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colors.background) {
                            Welcome()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countDown()

    }

    private fun countDown() {
        lifecycleScope.launchWhenCreated {
            viewModel.startCountDown()
            viewModel.countDown.collect {
                if (it <= 0) {
                    navigate(Screen.Home)
                }
            }
        }
    }

}