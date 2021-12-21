package com.slin.compose.study.ui.unite

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.slin.compose.study.ui.samples.LayoutItem
import com.slin.compose.study.ui.samples.MultiTestPage
import kotlin.random.Random

/**
 * author: slin
 * date: 2021/9/27
 * description:
 *
 */

@ExperimentalFoundationApi
@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun PagerTest() {

    val testItems = listOf(
        LayoutItem("1. HorizontalPagerTest") { HorizontalPagerTest() },
        LayoutItem("2. PagerSizeChangeTest") { PagerSizeChangeTest() },
        LayoutItem("3. HorizontalPagerTest") { HorizontalPagerTest() },
    )

    MultiTestPage(title = "PagerTest", testItems = testItems)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HorizontalPagerTest() {
    val pagerState = rememberPagerState()
    HorizontalPager(state = pagerState, modifier = Modifier
        .fillMaxSize()
        .height(200.dp)) { page ->
        PageItem(index = page)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PagerSizeChangeTest() {
    var count by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState()

    Column(modifier = Modifier
        .fillMaxSize()
        .height(200.dp)) {
        Button(onClick = { count = count.inc() }) {
            Text(text = "Page Size")
        }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            PageItem(index = page)
        }
    }
}

@Composable
private fun PageItem(index: Int) {
    Box(modifier = Modifier
        .background(Color(Random.nextInt()))
        .fillMaxHeight()
        .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Page: $index")
    }
}

