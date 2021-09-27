package com.slin.compose.study.ui.samples

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.slin.compose.study.R
import com.slin.compose.study.ui.NavDestinations
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import com.slin.compose.study.ui.unite.NavigationTest
import com.slin.compose.study.ui.unite.PagerTest
import com.slin.compose.study.ui.unite.ViewModelTest



/**
 * author: slin
 * date: 2021/3/11
 * description: 示例列表
 *
 */
data class SamplePage(
    val name: String,
    val icon: Int,
    val destination: String,
    val withTheme: Boolean = true,
    val content: @Composable () -> Unit
)

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
val samples = listOf(
    SamplePage("Layout", R.drawable.img_cartoon_1, NavDestinations.ROUTE_LAYOUT) { LayoutSample() },
    SamplePage("Theme", R.drawable.img_cartoon_2, NavDestinations.ROUTE_THEME, false) {
        ThemeSample()
    },
    SamplePage("List", R.drawable.img_cartoon_3, NavDestinations.ROUTE_LIST) { ListSample() },
    SamplePage("Text", R.drawable.img_cartoon_bear, NavDestinations.ROUTE_TEXT) { TextSample() },
    SamplePage("Graphic", R.drawable.img_cartoon_car, NavDestinations.ROUTE_GRAPHIC) {
        GraphicSample()
    },
    SamplePage("AnimationSample", R.drawable.img_cartoon_cat, NavDestinations.ROUTE_ANIM) {
        AnimationSample()
    },
    SamplePage("GestureSample", R.drawable.img_cartoon_man, NavDestinations.ROUTE_GESTURE) {
        GestureSample()
    },
    SamplePage("NavigationTest", R.drawable.img_cartoon_pig1, NavDestinations.ROUTE_NAV_TEST) {
        NavigationTest()
    },
    SamplePage(
        "ViewModelTest",
        R.drawable.img_cartoon_pig2,
        NavDestinations.ROUTE_VIEW_MODEL_TEST
    ) {
        ViewModelTest()
    },
    SamplePage(
        "PagerTest",
        R.drawable.img_cartoon_3,
        NavDestinations.ROUTE_PAGER_TEST
    ) {
        PagerTest()
    },
)

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Samples(onClickSample: (SamplePage) -> Unit) {
    ScaffoldWithCsAppBar(
        isShowBack = false,
        title = stringResource(id = R.string.app_name),
        actions = { Text(text = "by slin") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(innerPadding)
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 16.dp),
            ) {
                samples.forEach { sample ->
                    item { SampleItem(sample, onClickSample = onClickSample) }
                }
            }
        }
    }
}

@Composable
fun SampleItem(samplePage: SamplePage, onClickSample: (SamplePage) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = Size.mini, vertical = Size.mini)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClickSample(samplePage)
            },
        shape = RoundedCornerShape(16.dp), elevation = 5.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = samplePage.icon),
                contentDescription = samplePage.name
            )
            Text(text = samplePage.name, modifier = Modifier.padding(top = 10.dp))
        }
    }

}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview
@Composable
fun SampleItemPreview() {
    SampleItem(samplePage = samples[0]) {

    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview
@Composable
fun SamplePreview() {
    Samples {
    }
}

