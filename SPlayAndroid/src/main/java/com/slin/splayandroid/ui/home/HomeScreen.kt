package com.slin.splayandroid.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.slin.splayandroid.R


/**
 * author: slin
 * date: 2021/6/8
 * description: 首页
 * 搜索入口、新消息入口
 * 首页
 * 每日一问
 * 广场
 *
 */


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen() {


    Scaffold(
        topBar = { SearchTopBar() },
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .statusBarsPadding(),
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Column(modifier = Modifier) {
            val panelTitles = stringArrayResource(id = R.array.array_home_tabs)
            val pagerState = rememberPagerState(pageCount = panelTitles.size, initialPage = 1)
            var selectTabPosition by remember { mutableStateOf(pagerState.currentPage) }

            LaunchedEffect(key1 = selectTabPosition) {
                pagerState.scrollToPage(page = selectTabPosition)
            }

            TabRow(selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(
                        pagerState = pagerState,
                        tabPositions
                    )
                )
            }) {
                panelTitles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = { selectTabPosition = index },
                    )
                }
            }
            HorizontalPager(state = pagerState, modifier = Modifier.padding(top = 8.dp)) { page ->
                when (page) {
                    0 -> DailyQuestionPanel()
                    1 -> HomePanel()
                    2 -> PiazzaPanel()
                }
            }
        }
    }
}

/**
 * 顶部搜索和消息入口
 */
@Preview
@Composable
fun SearchTopBar() {
    var searchText by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .weight(1f)
                .height(32.dp)
                .background(
                    color = MaterialTheme.colors.surface.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "Search",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp)
                )
                BasicTextField(value = searchText, onValueChange = { text -> searchText = text })
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_mail_outline_24),
            contentDescription = "Message",
            modifier = Modifier
                .height(48.dp)
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        )
    }

}
