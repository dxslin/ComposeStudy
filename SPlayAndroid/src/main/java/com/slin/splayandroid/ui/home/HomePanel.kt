package com.slin.splayandroid.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.*
import com.slin.core.utils.fromJsonArray
import com.slin.splayandroid.data.bean.ArticleBean
import com.slin.splayandroid.data.bean.BannerBean
import com.slin.splayandroid.ui.home.vm.HomeViewModel
import com.slin.splayandroid.widget.NetworkImage
import com.slin.splayandroid.widget.PageList
import kotlin.math.absoluteValue

/**
 * author: slin
 * date: 2021/8/30
 * description: 首页列表和Banner
 *
 */

@Composable
fun HomePanel(onItemClick: (ArticleBean) -> Unit) {
    val homeViewModel: HomeViewModel = viewModel()

    val banners by homeViewModel.bannerFlow.collectAsState()
    val homeArticles = homeViewModel.homeArticleFlow.collectAsLazyPagingItems()


    PageList(lazyPagingItems = homeArticles, headerContent = {

        Banner(banners = banners)

        Spacer(modifier = Modifier.height(16.dp))

    }) { _, item ->
        ArticleItem(articleBean = item, onItemClick = onItemClick)
    }

}



@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(banners: List<BannerBean>) {
    if (banners.isEmpty()) return
    val state = rememberPagerState(pageCount = banners.size, infiniteLoop = true)

    Box(modifier = Modifier) {
        HorizontalPager(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.8f)
        ) { page ->
            BannerItem(page = page, banner = banners[page])
        }

        HorizontalPagerIndicator(
            pagerState = state,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            activeColor = MaterialTheme.colors.secondary,
            indicatorWidth = 8.dp,
            indicatorHeight = 8.dp,
        )

    }
}

@ExperimentalPagerApi
@Composable
fun PagerScope.BannerItem(page: Int, banner: BannerBean) {
    Box(
        Modifier
            .graphicsLayer {
                // 参考 https://google.github.io/accompanist/pager/#item-scroll-effects
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

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
            .fillMaxWidth(0.8f)
            .clip(shape = MaterialTheme.shapes.small)
    ) {
        NetworkImage(
            url = banner.imagePath,
            contentDescription = banner.desc,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface.copy(alpha = 0.5f))
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = banner.title,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
            )

        }
    }
}


@Preview
@Composable
fun PreviewBanner() {
    val bannersData =
        "[{\"desc\":\"扔物线\",\"id\":29,\"imagePath\":\"https://wanandroid.com/blogimgs/5d362c2a-2e9e-4448-8ee4-75470c8c7533.png\",\"isVisible\":1,\"order\":0,\"title\":\"LiveData：还没普及就让我去世？我去你的 Kotlin 协程\",\"type\":0,\"url\":\"https://url.rengwuxian.com/y3zsb\"},{\"desc\":\"\",\"id\":6,\"imagePath\":\"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png\",\"isVisible\":1,\"order\":1,\"title\":\"我们新增了一个常用导航Tab~\",\"type\":1,\"url\":\"https://www.wanandroid.com/navi\"},{\"desc\":\"一起来做个App吧\",\"id\":10,\"imagePath\":\"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png\",\"isVisible\":1,\"order\":1,\"title\":\"一起来做个App吧\",\"type\":1,\"url\":\"https://www.wanandroid.com/blog/show/2\"},{\"desc\":\"\",\"id\":20,\"imagePath\":\"https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png\",\"isVisible\":1,\"order\":2,\"title\":\"flutter 中文社区 \",\"type\":1,\"url\":\"https://flutter.cn/\"}]"
    val banners: List<BannerBean> = bannersData.fromJsonArray()
    println("banners: $banners")
    Banner(banners = banners)
}
