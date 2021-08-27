package com.slin.splayandroid.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.slin.core.logger.log
import com.slin.splayandroid.data.bean.BannerBean
import com.slin.splayandroid.widget.NetworkImage
import kotlin.math.absoluteValue

/**
 * author: slin
 * date: 2021/6/8
 * description:
 *
 */

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = viewModel()

    val name by homeViewModel.name.observeAsState()
    val count by homeViewModel.count.observeAsState()
    val banners by homeViewModel.bannerFlow.collectAsState()


    Scaffold {
        Column {

            Banner(banners = banners)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "$name: $count")

        }

    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(banners: List<BannerBean>) {
    if (banners.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = banners.size, infiniteLoop = true)

    val state by remember { mutableStateOf(pagerState) }

    log { "banners: $banners " }
    HorizontalPager(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.8f)
    ) { page ->
        BannerItem(banner = banners[page], state = state)
    }
}

@ExperimentalPagerApi
@Composable
fun BannerItem(banner: BannerBean, state: PagerState) {
    log { "banner: $banner" }
    Box(
        Modifier
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset = state.currentPageOffset.absoluteValue

                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
    ) {
        NetworkImage(
            url = banner.imagePath,
            contentDescription = banner.desc,
            Modifier.fillMaxWidth()
        )
        Text(
            text = banner.title ?: "",
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

