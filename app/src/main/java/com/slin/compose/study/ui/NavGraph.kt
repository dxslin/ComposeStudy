package com.slin.compose.study.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.*
import com.slin.compose.study.ui.samples.LayoutSample
import com.slin.compose.study.ui.samples.Samples
import com.slin.compose.study.ui.samples.ThemeSample
import com.slin.compose.study.ui.samples.samples
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets


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

}


@ExperimentalFoundationApi
@Composable
fun NavGraph(startDestination: String = NavDestinations.ROUTE_SAMPLES) {
    val navController = rememberNavController()

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


@Composable
private fun WithTheme(
    content: @Composable () -> Unit
) {
    ComposeStudyTheme {
        content()

    }
}


