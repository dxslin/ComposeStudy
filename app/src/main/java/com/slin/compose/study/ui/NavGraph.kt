package com.slin.compose.study.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slin.compose.study.ui.samples.Samples
import com.slin.compose.study.ui.samples.samples
import com.slin.compose.study.ui.theme.ComposeStudyTheme


/**
 * author: slin
 * date: 2021/3/11
 * description:
 *
 */

/**
 * 导航目的地页面 路径
 */
object NavDestinations {

    const val ROUTE_SAMPLES = "route_samples"
    const val ROUTE_LAYOUT = "route_layout"
    const val ROUTE_THEME = "route_theme"
    const val ROUTE_LIST = "route_list"
    const val ROUTE_TEXT = "route_text"
    const val ROUTE_GRAPHIC = "route_graphic"
    const val ROUTE_ANIM = "route_anim"
    const val ROUTE_GESTURE = "route_gesture"

    const val ROUTE_NAV_TEST = "route_nav_test"
    const val ROUTE_VIEW_MODEL_TEST = "route_view_model_test"
    const val ROUTE_PAGER_TEST = "route_pager_test"

}

val LocalNavController = compositionLocalOf<NavController?> { null }

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun NavGraph(startDestination: String = NavDestinations.ROUTE_SAMPLES) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(route = NavDestinations.ROUTE_SAMPLES) {
                WithTheme {
                    Samples { sample ->
                        navController.navigate(sample.destination)
                    }
                }
            }

            samples.forEach { page ->
                composable(route = page.destination) {
                    if (page.withTheme) {
                        WithTheme {
                            page.content()
                        }
                    } else {
                        page.content()
                    }
                }
            }
        }
    }

}


@Composable
private fun WithTheme(
    content: @Composable () -> Unit
) {
    ComposeStudyTheme {
        content()

    }
}


