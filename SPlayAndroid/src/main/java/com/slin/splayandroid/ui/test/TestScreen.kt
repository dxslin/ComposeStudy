package com.slin.splayandroid.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.slin.splayandroid.R
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.ui.home.ArticleItem
import com.slin.splayandroid.ui.home.HomePanel
import com.slin.splayandroid.widget.RefreshPageList
import kotlin.random.Random

/**
 * author: slin
 * date: 2021/9/17
 * description:
 *
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TestScreen(
    onItemClick: (ArticleBean) -> Unit
) {

    // 含有CompositionLocalProvider和HorizontalPager的组合，如果使用到context相关的东西，容易导致绘制出现问题
    // 比如下面使用到了stringArrayResource，
    CompositionLocalProvider {
        val panelTitles = stringArrayResource(id = R.array.array_home_tabs)
        val pagerState = rememberPagerState(pageCount = panelTitles.size)

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                1 -> Test(onItemClick = onItemClick)
                0 -> HomePanel(onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun Test(onItemClick: (ArticleBean) -> Unit) {
    val testViewModel: TestViewModel = hiltViewModel()
    val items = testViewModel.testArticleFlow.collectAsLazyPagingItems()
//    val items = testViewModel.collectCacheLazyPagingItems(flow = testViewModel.testArticleFlow)

    RefreshPageList(items = items) { _, article ->
        ArticleItem(articleBean = article, onItemClick = onItemClick)
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