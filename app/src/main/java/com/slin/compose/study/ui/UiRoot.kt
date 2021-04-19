package com.slin.compose.study.ui

import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets


/**
 * author: slin
 * date: 2021/4/14
 * description:
 *
 */

@ExperimentalFoundationApi
@Composable
fun ComposeStudyUiRoot(backPressedDispatcher: OnBackPressedDispatcherOwner) {
    CompositionLocalProvider(LocalOnBackPressedDispatcherOwner provides backPressedDispatcher) {
        ProvideWindowInsets {
            NavGraph()
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProvideWindowInsets {
        ComposeStudyTheme {

            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                NavGraph()
            }
        }
    }
}