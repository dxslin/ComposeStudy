package com.slin.splayandroid.nav

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.slin.core.logger.logd
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ui.detail.ArticleDetailScreen
import com.slin.splayandroid.ui.home.HomeScreen
import com.slin.splayandroid.ui.home.vm.HomeViewModel
import com.slin.splayandroid.ui.splash.SplashViewModel
import com.slin.splayandroid.ui.splash.WelcomeScreen

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
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavGraphs() {
    val navController = rememberNavController()

    val actions: Actions = remember(navController) { Actions(navController) }

    val homeViewModel: HomeViewModel = viewModel()

    val homeArticles = homeViewModel.homeArticleFlow.collectAsLazyPagingItems()
    logd { "1 currentComposer: $currentComposer \t " }
    NavHost(
        navController = navController,
        startDestination = MainDestinations.Splash,
        modifier = Modifier
    ) {
        composable(MainDestinations.Splash) {
            val splashViewModel: SplashViewModel = hiltViewModel()
            WelcomeScreen(splashViewModel = splashViewModel, startHome = actions.navigateToHome)
        }
//        logd { "2 currentComposer: $currentComposer" }
        composable(MainDestinations.Home) {
            logd { "3 currentComposer: $currentComposer \t ${currentComposer.changed(homeViewModel.homeArticleFlow)}" }
            HomeScreen(
                homeViewModel = homeViewModel,
                onItemClick = actions.navigateToArticleDetail,
                homeArticles
            )
        }
        composable(MainDestinations.Category) {
            Text(text = MainDestinations.Category, modifier = Modifier.fillMaxSize())
        }
        composable(MainDestinations.Project) {
            Text(text = MainDestinations.Project, modifier = Modifier.fillMaxSize())
        }
        composable(
            route = "${MainDestinations.ArticleDetail}/{title}/{url}",
            arguments = listOf(
                navArgument("url") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType })
        ) {
            it.arguments?.apply {
                ArticleDetailScreen(
                    mTitle = getString("title", ""),
                    mUrl = Uri.decode(getString("url", ""))
                )
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
            "${MainDestinations.ArticleDetail}/${article.title}/${
                Uri.encode(
                    article.link
                )
            }"
        )
    }

    val upPress: () -> Unit = { navController.navigateUp() }


}

// Actions
// ViewModel
// UiState