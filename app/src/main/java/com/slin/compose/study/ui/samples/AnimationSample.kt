package com.slin.compose.study.ui.samples

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

/**
 * author: slin
 * date: 2021/4/29
 * description: 动画使用示例
 *
 */

@ExperimentalAnimationApi
@Preview
@Composable
fun AnimationSample() {
    val testItems = listOf(
        LayoutItem("1. SimpleAnim") { SimpleAnim() },
        LayoutItem("2. Clock") { SimpleClock() },
    )


    ScaffoldWithCsAppBar(title = "AnimationSample") { innerPadding ->
        AnimatedVisibility(visible = true, enter = fadeIn() + slideInHorizontally()) {
            LazyColumn(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = Size.medium)
            ) {
                items(testItems) {
                    TestItem(item = it)
                }
            }

        }
    }
}


@ExperimentalAnimationApi
@Composable
fun SimpleAnim() {
    Column {
        val show = remember { mutableStateOf(true) }
        Row(modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)) {
            Button(onClick = { show.value = !show.value }) {
                Text(text = "点击启动动画")
            }
            AnimatedVisibility(
                modifier = Modifier.padding(start = 16.dp),
                visible = show.value,
                enter = fadeIn(0.3f) + slideInHorizontally(),
                exit = fadeOut(0.3f) + shrinkHorizontally() + slideOutHorizontally()
            ) {
                Text(text = "隐藏显示动画")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = if (show.value) "动画：无" else "动画：这里是对照组，无动画")
        Text(
            text = if (show.value) "动画：有" else "动画：这里是实验组，有动画",
            modifier = Modifier.animateContentSize()
        )
        Crossfade(targetState = show.value) {
            if (it) {
                Text(text = "Page")
            } else {
                Text(text = "Fragment")
            }
        }

    }

}

