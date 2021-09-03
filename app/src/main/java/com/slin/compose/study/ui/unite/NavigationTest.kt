package com.slin.compose.study.ui.unite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.slin.compose.study.R
import com.slin.compose.study.ui.LocalNavController
import com.slin.compose.study.ui.NavDestinations
import com.slin.compose.study.ui.samples.LayoutItem
import com.slin.compose.study.ui.samples.MultiTestPage
import kotlinx.coroutines.launch

/**
 * author: slin
 * date: 2021/5/24
 * description: 测试导航
 *
 */


@Preview
@Composable
fun NavigationTest() {

    val testItems = listOf(
        LayoutItem("Navigation") { NavigationToPage() },
        LayoutItem("BottomNavigationTest") { BottomNavigationTest() },
    )

    MultiTestPage(title = "1. NavigationTest", testItems = testItems)
}

@Preview
@Composable
private fun NavigationToPage() {
    val navController = LocalNavController.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            navController?.navigate(NavDestinations.ROUTE_THEME) {
                anim {
                    enter = R.anim.anim_scale_alpha_in
                    exit = R.anim.anim_scale_alpha_out
                    popEnter = R.anim.anim_bottom_dialog_enter_from_bottom
                    popExit = R.anim.anim_bottom_dialog_exit_to_bottom
                }
            }
        }) {
            Text(text = "nav to theme")
        }
    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun BottomNavigationTest() {
    val pagerState = rememberPagerState(pageCount = 3)
    val coroutineScope = rememberCoroutineScope()
    val animateToPage: (Int) -> Unit = { page ->
        coroutineScope.launch { pagerState.animateScrollToPage(page) }
    }

    Column(modifier = Modifier.height(300.dp)) {
        val colors = listOf(Color.Magenta, Color.Yellow, Color.Cyan)
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) {
            Text(
                text = "当前处于第${pagerState.currentPage}页",
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors[pagerState.currentPage])
            )
        }

        BottomNavigation(modifier = Modifier.fillMaxWidth()) {
            BottomNavigationItem(selected = true, onClick = { animateToPage(0) }, icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            })
            BottomNavigationItem(selected = true, onClick = { animateToPage(1) }, icon = {
                Icon(
                    imageVector = Icons.Default.Dashboard,
                    contentDescription = "Dashboard"
                )
            })
            BottomNavigationItem(selected = true, onClick = { animateToPage(2) }, icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            })
        }
    }


}
