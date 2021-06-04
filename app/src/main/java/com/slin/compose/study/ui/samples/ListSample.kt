package com.slin.compose.study.ui.samples

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.slin.compose.study.R
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import com.slin.core.logger.logd

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * author: slin
 * date: 2021/3/12
 * description:列表
 *
 */

val fruits = listOf("apple", "apricot", "banana", "cherry", "peach", "pear")
val photos = listOf(
    R.drawable.img_cartoon_1, R.drawable.img_cartoon_2, R.drawable.img_cartoon_3,
    R.drawable.img_cartoon_bear, R.drawable.img_cartoon_car, R.drawable.img_cartoon_cat,
    R.drawable.img_cartoon_man, R.drawable.img_cartoon_pig1, R.drawable.img_cartoon_pig2,
    R.drawable.img_prairie, R.drawable.img_ice_princess, R.drawable.img_fate_arthur,
)

@ExperimentalFoundationApi
@Composable
fun ListSample() {

    val testItems = listOf(
        LayoutItem("1. RowScroll") { RowScroll() },
        LayoutItem("2. LazyRowScroll") { LazyRowScroll() },
        LayoutItem("3. ListWithHeader") { ListWithHeader() },
        LayoutItem("4. ListWithSticky") { ListWithSticky() },
        LayoutItem("5. PhotoGrid") { PhotoGrid() },
    )

    ScaffoldWithCsAppBar(title = "ListSample") { innerPadding ->
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(innerPadding)
                .verticalScroll(state = rememberScrollState())
                .padding(bottom = Size.medium)
        ) {
            testItems.forEach { item ->
                TestItem(item = item)
            }
        }
    }
}

/**
 *
 * 1. Row/Column本身无法滚动，但是配置Modifier.horizontalScroll/verticalScroll后即可滚动
 */
@Preview
@Composable
fun RowScroll() {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(
                state = scrollState,
                enabled = true,
                flingBehavior = object : FlingBehavior {
                    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                        logd { "initialVelocity = $initialVelocity" }
                        return initialVelocity
                    }

                },
                reverseScrolling = false
            )
    ) {
        fruits.forEachIndexed { index, text ->
            ProvideTextStyle(value = ComposeStudyTheme.typography.body1) {
                if (index > 0) {
                    Spacer(modifier = Modifier.width(Size.large))
                }
                Text(text = text)
            }
        }
    }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .collect { state ->
                logd { "scrollState: $state" }
            }
    }
}

@Preview
@Composable
fun LazyRowScroll() {
    val listState = rememberLazyListState()
    LazyRow(state = listState, horizontalArrangement = Arrangement.spacedBy(Size.large)) {
        items(fruits) { fruit ->
            Text(text = fruit)
        }
    }
    LaunchedEffect(listState){
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { logd { "listState.firstVisibleItemIndex = $it" } }
    }

}

@ExperimentalFoundationApi
@Preview
@Composable
fun ListWithHeader() {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        stickyHeader {
            Text(
                text = "Fruits",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray)
                    .clickable {
                        scope.launch { listState.animateScrollToItem(0) }
                    }
            )
        }
        items(fruits) { fruit ->
            Text(text = fruit)
        }
    }
}


@ExperimentalFoundationApi
@Preview
@Composable
fun ListWithSticky() {
    val grouped = fruits.groupBy { it.first() }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        grouped.forEach { (group, fruits) ->
            stickyHeader {
                Text(
                    text = group.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                )
            }
            items(fruits) { fruit ->
                Text(text = fruit)
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PhotoGrid() {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(96.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        items(photos) { photo ->
            Image(painter = painterResource(id = photo), contentDescription = null)
        }
    }
}


