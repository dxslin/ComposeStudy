package com.slin.compose.study.ui.unite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.slin.compose.study.R
import com.slin.compose.study.ui.LocalNavController
import com.slin.compose.study.ui.NavDestinations
import com.slin.compose.study.ui.samples.LayoutItem
import com.slin.compose.study.ui.samples.MultiTestPage

/**
 * author: slin
 * date: 2021/5/24
 * description: 测试导航
 *
 */


@Preview
@Composable
fun NavigationTest() {

    val testItems = listOf(LayoutItem("Navigation") { NavigationToPage() })

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