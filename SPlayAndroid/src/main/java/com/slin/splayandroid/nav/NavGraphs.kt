package com.slin.splayandroid.nav

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ui.detail.ArticleDetailScreen
import com.slin.splayandroid.ui.home.HomeScreen
import com.slin.splayandroid.ui.splash.SplashViewModel
import com.slin.splayandroid.ui.splash.WelcomeScreen
import com.slin.splayandroid.ui.test.TestScreen

/**
 * author: slin
 * date: 2021/8/10
 * description:
 *
 */

object MainDestinations {
    const val Splash = "Splash"
    const val Home = "Home"
    const val Project = "Project"
    const val Category = "Category"
    const val ArticleDetail = "ArticleDetail"
    const val Test = "Test"
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavGraphs() {
    val navController = rememberNavController()

    val actions: Actions = remember(navController) { Actions(navController) }

    NavHost(
        navController = navController,
        startDestination = MainDestinations.Home,
        modifier = Modifier
    ) {
        composable(MainDestinations.Splash) {
            val splashViewModel: SplashViewModel = hiltViewModel()
            WelcomeScreen(splashViewModel = splashViewModel, startHome = actions.navigateToHome)
        }
        composable(MainDestinations.Home) {
            HomeScreen(
                onItemClick = actions.navigateToArticleDetail,
            )
        }
        composable(MainDestinations.Test) {
            TestScreen(
                onItemClick = actions.navigateToArticleDetail,
            )
        }
        composable(MainDestinations.Category) {
            Text(text = MainDestinations.Category, modifier = Modifier.fillMaxSize())
        }
        composable(MainDestinations.Project) {
            Text(text = MainDestinations.Project, modifier = Modifier.fillMaxSize())
        }
        composable(
            route = "${MainDestinations.ArticleDetail}?title={title}&url={url}",
            arguments = listOf(
                navArgument("url") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType })
        ) {
            it.arguments?.apply {
                ArticleDetailScreen(
                    mTitle = Uri.decode(getString("title", "")),
                    mUrl = Uri.decode(getString("url", "")),
                    onBackPress = { navController.navigateUp() }
                )
            }
            BackHandler {
                navController.navigateUp()
            }
        }
    }
}


class Actions(private val navController: NavController) {

    val navigateToHome: () -> Unit = {
        navController.navigate(MainDestinations.Home)
    }

    val navigateToArticleDetail: (ArticleBean) -> Unit = { article ->
        navController.navigate(
            "${MainDestinations.ArticleDetail}?" +
                    "title=${Uri.encode(article.title)}&" +
                    "url=${Uri.encode(article.link)}"
        )
    }

    val upPress: () -> Unit = { navController.navigateUp() }


}

// Actions
// ViewModel
// UiState