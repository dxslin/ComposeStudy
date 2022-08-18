package com.slin.compose.study.ui.samples

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.insets.navigationBarsPadding
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import com.slin.core.logger.log

@Composable
fun DialogSample() {
    val testItems = listOf(
        LayoutItem("1. SimpleAnim") { SimpleDialog() }
    )

    ScaffoldWithCsAppBar(title = "AnimationSample") { innerPadding ->
        AnimatedVisibility(visible = true, enter = slideInHorizontally()) {
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

@Composable
fun SimpleDialog() {
    Dialog(onDismissRequest = {
        log { "onDismissRequest" }
    }) {
        Column(modifier = Modifier.size(200.dp, 100.dp).background(Color.Red)) {
            Text(text = "标题", style = ComposeStudyTheme.typography.h5)
            Text(text = "这是消息", style = ComposeStudyTheme.typography.body2)
        }
    }
}
