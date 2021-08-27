package com.slin.splayandroid.ui.splash

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.slin.splayandroid.base.ComposeFragment
import com.slin.splayandroid.nav.Screen
import com.slin.splayandroid.nav.navigate
import kotlinx.coroutines.flow.collect

/**
 * author: slin
 * date: 2021/8/23
 * description:
 *
 */
class SplashFragment : ComposeFragment() {

    private val viewModel: SplashViewModel by viewModels()

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
            viewModel.countFlow.collect {
                if (it <= 0) {
                    navigate(Screen.Home)
                }
            }
        }
    }

}