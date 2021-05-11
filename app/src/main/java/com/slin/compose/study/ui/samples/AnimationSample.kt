package com.slin.compose.study.ui.samples

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import com.slin.compose.study.weight.Spinner
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

/**
 * author: slin
 * date: 2021/4/29
 * description: 动画使用示例
 *
 */

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview
@Composable
fun AnimationSample() {
    val testItems = listOf(
        LayoutItem("1. SimpleAnim") { SimpleAnim() },
        LayoutItem("2. SimpleAnimAsState") { SimpleAnimAsState() },
        LayoutItem("3. SimpleAnimatable") { SimpleAnimatable() },
        LayoutItem("3. SimpleUpdateTransition") { SimpleUpdateTransition() },
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


/**
 * 已经封装好的几个动画
 * 1. AnimatedVisibility 隐藏显示的时候的动画，进入退出时可以多个动画组合在一起
 * 2. Modifier.animateContentSize 这个大小改变时有动画
 * 3. Crossfade 淡入淡出动画
 */
@ExperimentalAnimationApi
@Composable
fun SimpleAnim() {
    Column {
        val show = remember { mutableStateOf(true) }
        Row(modifier = Modifier) {
            Button(onClick = { show.value = !show.value }) {
                Text(text = "点击启动动画")
            }
            AnimatedVisibility(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .align(Alignment.CenterVertically),
                visible = show.value,
                enter = fadeIn(0.3f) + slideInHorizontally(),
                exit = fadeOut(0.3f) + shrinkHorizontally() + slideOutHorizontally()
            ) {
                Text(text = "隐藏显示动画")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = if (show.value) "animateContentSize：无" else "animateContentSize：对照组，无动画")
        Text(
            text = if (show.value) "animateContentSize：有" else "animateContentSize：实验组，有动画",
            modifier = Modifier.animateContentSize()
        )
        Crossfade(targetState = show.value) {
            if (it) {
                Text(text = "Crossfade Page")
            } else {
                Text(text = "Crossfade Fragment")
            }
        }

    }

}

/**
 * Compose 封装了很多开箱即用的Animate*AsState，
 * 包括 Float、Color、Dp、Size、Bounds、Offset、Rect、Int、IntOffset 和 IntSize <p>
 * 使用时只需传入可修改的对应值即可，后续修改该变量时，都会引起对应界面的变化
 */
@ExperimentalMaterialApi
@Preview
@Composable
fun SimpleAnimAsState() {
    val progress = remember { mutableStateOf(1f) }
    val alpha by animateFloatAsState(targetValue = progress.value)

    var mutableColor by remember { mutableStateOf(Color.Red) }
    val color by animateColorAsState(targetValue = mutableColor)

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "alpha", modifier = Modifier.align(Alignment.CenterVertically))
            Slider(
                value = progress.value, onValueChange = {
                    progress.value = it
                }, modifier = Modifier
                    .padding(start = Size.small)
                    .weight(1f)
            )
            Text(
                text = String.format("%.2f", progress.value),
                modifier = Modifier
                    .padding(start = Size.small)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .align(Alignment.CenterVertically)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "color", modifier = Modifier.align(Alignment.CenterVertically))
            Slider(
                value = 1 - mutableColor.red, onValueChange = {
                    mutableColor = Color(1 - it, 0f, 0f)
                }, modifier = Modifier
                    .padding(start = Size.small)
                    .weight(1f)
            )
            Text(
                text = String.format("%.2f", 1 - mutableColor.red),
                modifier = Modifier
                    .padding(start = Size.small)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .align(Alignment.CenterVertically)
            )
        }
        Box(
            modifier = Modifier
                .alpha(alpha = alpha)
                .background(color = color)
                .size(50.dp, 50.dp)
        )

    }

}


/**
 * Animatable <p>
 * 1. animateTo 变化至目标值，可以通过`animationSpec`设置变化的动画
 * 2. snapTo 立即将当前值设置为目标值
 * 3. animateDecay 用于启动播放从给定速度变慢的动画
 */
@Preview
@Composable
fun SimpleAnimatable() {
    val animTypes = listOf("animateTo", "snapTo", "animateDecay")

    var type by remember { mutableStateOf(animTypes[0]) }
    var ok by remember { mutableStateOf(false) }
    val color = remember { Animatable(Color.Gray) }
    val context = LocalContext.current

    Column {
        Spinner(list = animTypes, onTextChange = {
            type = it
        }, modifier = Modifier.width(180.dp))
        Switch(checked = ok, onCheckedChange = {
            ok = it
        })
        Text(text = "对照组，无颜色变化动画", color = if (ok) Color.Green else Color.Red)
        Text(text = "实验组，有颜色变化动画", color = color.value)
        LaunchedEffect(key1 = ok) {
            val c = if (ok) Color.Green else Color.Red
            when (type) {
                "animateTo" ->
                    color.animateTo(c, animationSpec = tween(durationMillis = 3000))
                "snapTo" ->
                    color.snapTo(c)
                "animateDecay" ->
                    color.animateDecay(
                        c,
                        animationSpec = splineBasedDecay(density = Density(context = context)),
                    )
            }
        }
    }
}

/**
 * updateTransition 通过状态管理一个或多个动画 <p>
 * 使用`updateTransition`监听摸个变量变化，创建多个可变的动画
 *
 */
@Preview
@Composable
fun SimpleUpdateTransition() {
    var progress by remember { mutableStateOf(1f) }
    val transition = updateTransition(targetState = progress, label = "progress")

    val width by transition.animateDp(label = "Width") {
        (it * 200).toInt().dp
    }
    val color by transition.animateColor(label = "Color") {
        Color(it, 0f, 0f)
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "alpha", modifier = Modifier.align(Alignment.CenterVertically))
            Slider(
                value = progress, onValueChange = {
                    progress = it
                }, modifier = Modifier
                    .padding(start = Size.small)
                    .weight(1f)
            )
            Text(
                text = String.format("%.2f", progress),
                modifier = Modifier
                    .padding(start = Size.small)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .align(Alignment.CenterVertically)
            )
        }
        Box(
            modifier = Modifier
                .size(width, 20.dp)
                .background(color = color)
                .size(50.dp, 50.dp)
        )
    }
}
