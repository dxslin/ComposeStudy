package com.slin.splayandroid.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.slin.splayandroid.base.ComposeFragment
import com.slin.splayandroid.nav.Screen
import com.slin.splayandroid.nav.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */
@AndroidEntryPoint
class WelcomeFragment : ComposeFragment() {

    private val viewModel: WelcomeViewModel by viewModels()

    @Composable
    override fun ComposeFragment.Content() {
        WelcomeScreen()
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