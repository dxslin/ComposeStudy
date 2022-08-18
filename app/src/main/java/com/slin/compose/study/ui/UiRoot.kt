package com.slin.compose.study.ui

import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ProvideWindowInsets
import com.slin.compose.study.ui.theme.ComposeStudyTheme


/**
 * author: slin
 * date: 2021/4/14
 * description:
 *
 */



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