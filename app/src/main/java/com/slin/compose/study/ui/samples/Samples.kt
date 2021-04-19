package com.slin.compose.study.ui.samples

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.slin.compose.study.R
import com.slin.compose.study.ui.NavDestinations
import com.slin.compose.study.ui.theme.CsAppBar
import com.slin.core.logger.logd
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding


/**
 * author: slin
 * date: 2021/3/11
 * description:
 *
 */
data class SamplePage(val name: String, val icon: Int, val destination: String)

val samples = listOf(
    SamplePage("Layout", R.drawable.img_cartoon_1, NavDestinations.ROUTE_LAYOUT),
    SamplePage("Theme", R.drawable.img_cartoon_2, NavDestinations.ROUTE_THEME),

    )

@ExperimentalFoundationApi
@Composable
fun Samples(onClickSample: (SamplePage) -> Unit) {
    Scaffold(
        topBar = {
            CsAppBar(
                title = stringResource(id = R.string.app_name),
                actions = {
                    Text(text = "by slin")
                })
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
    ) { innerPadding ->
        logd { "innerPadding = $innerPadding" }
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(innerPadding)
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 16.dp)
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

    Row {
        Spacer(
            modifier = Modifier
                .width(8.dp)
                .wrapContentHeight()
        )
        Card(
            modifier = Modifier
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
        Spacer(
            modifier = Modifier
                .width(8.dp)
                .wrapContentHeight()
        )
    }

}

@Preview
@Composable
fun SampleItemPreview() {
    SampleItem(samplePage = samples[0]) {

    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun SamplePreview() {
    Samples {
    }
}

