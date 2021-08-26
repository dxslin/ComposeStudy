package com.slin.splayandroid.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slin.splayandroid.ui.welcome.WelcomeScreen

/**
 * author: slin
 * date: 2021/8/10
 * description:
 *
 */

@Composable
fun NavGraphs() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "welcome",
        modifier = Modifier
    ) {
        composable("welcome") {
            WelcomeScreen()
        }

    }
}