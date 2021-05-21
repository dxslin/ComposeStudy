package com.slin.compose.study.ui.samples

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import com.slin.compose.study.weight.Spinner
import com.slin.core.logger.logd
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * author: slin
 * date: 2021/4/29
 * description: 动画使用示例
 *
 */

@ExperimentalMaterialApi
@ExperimentalAnimationApi
//@Preview
@Composable
fun AnimationSample() {
    val testItems = listOf(
        LayoutItem("1. SimpleAnim") { SimpleAnim() },
        LayoutItem("2. SimpleAnimAsState") { SimpleAnimAsState() },
        LayoutItem("3. SimpleAnimatable") { SimpleAnimatable() },
        LayoutItem("4. SimpleUpdateTransition") { SimpleUpdateTransition() },
        LayoutItem("5. SimpleInfiniteAnimation") { SimpleInfiniteAnimation() },
        LayoutItem("6. SimpleTargetBaseAnimation") { SimpleTargetBaseAnimation() },
        LayoutItem("7. SimpleAnimationSpecs") { SimpleAnimationSpecs() },
        LayoutItem("8. SimplePointerInput") { SimplePointerInput() },
        LayoutItem("9. SimpleSwipeToDismiss") { SimpleSwipeToDismiss() },
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
 * Compose 封装了很多开箱即用的animate*AsState，
 * 包括 Float、Color、Dp、Size、Bounds、Offset、Rect、Int、IntOffset 和 IntSize <p>
 * 使用时只需传入可修改的对应值即可，后续修改该变量时，都会引起对应界面的变化
 */
@ExperimentalMaterialApi
//@Preview
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
//@Preview
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
 * 使用`updateTransition`监听某个变量变化，创建多个可变的动画
 *
 */
//@Preview
@Composable
fun SimpleUpdateTransition() {
    var progress by remember { mutableStateOf(0.5f) }
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

/**
 * rememberInfiniteTransition 无线循环的动画
 */
//@Preview
@Composable
fun SimpleInfiniteAnimation() {
    val infiniteAnimation = rememberInfiniteTransition()
    val color by infiniteAnimation.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .size(100.dp, 20.dp)
            .background(color = color)
    )
}

/**
 * TargetBasedAnimation 自己控制时间的动画
 */
//@Preview
@Composable
fun SimpleTargetBaseAnimation() {
    val anim = remember {
        TargetBasedAnimation(
//            animationSpec = tween(5000),
            animationSpec = spring(dampingRatio = 0.4f, stiffness = Spring.StiffnessLow),
            typeConverter = Float.VectorConverter,
            initialValue = 0f,
            targetValue = 1f,
        )
    }
    var playTime by remember { mutableStateOf(0L) }
    var animationValue by remember { mutableStateOf(0f) }

    var started by remember { mutableStateOf(true) }

    Column {
        Checkbox(checked = started, onCheckedChange = {
            started = it
        })
        LinearProgressIndicator(
            progress = animationValue,
            modifier = Modifier
                .padding(top = Size.small)
                .size(160.dp, 10.dp)
                .padding(end = Size.medium)
        )

    }

    LaunchedEffect(key1 = anim, key2 = started) {
        val startTime = withFrameNanos { it }
        do {
            playTime = withFrameNanos { it } - startTime
            animationValue = anim.getValueFromNanos(playTime)
            logd { "playTime = $playTime animationValue = $animationValue" }
        } while (started)
    }
}


//@Preview
@Composable
fun SimpleAnimationSpecs() {
    class SpecItem(val name: String, val progress: Float)

    var progress by remember { mutableStateOf(0f) }

    //spring 可在起始值和结束值之间创建基于物理特性的动画。它接受 2 个参数：dampingRatio 和 stiffness。
    //dampingRatio 定义弹簧的弹性
    //stiffness 定义弹簧应向结束值移动的速度。
    val springHigh by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = 0.2f, stiffness = Spring.DampingRatioHighBouncy)
    )
    val springMedium by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = Spring.DampingRatioMediumBouncy)
    )
    val springLow by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = Spring.DampingRatioLowBouncy)
    )
    val springNo by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = 2.0f, stiffness = Spring.DampingRatioNoBouncy)
    )

    //tween 在指定的 durationMillis 内使用缓和曲线在起始值和结束值之间添加动画效果
    val tween by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, delayMillis = 50, easing = FastOutLinearInEasing)
    )

    //keyframes 会根据在动画时长内的不同时间戳中指定的快照值添加动画效果
    val keyframe by animateFloatAsState(
        targetValue = progress,
        animationSpec = keyframes {
            durationMillis = 1000
            delayMillis = 50
            0.1f at 50 with LinearOutSlowInEasing // for 0-15 ms
            0.2f at 200 with FastOutLinearInEasing // for 15-75 ms
            0.4f at 400 with LinearOutSlowInEasing // ms
            0.6f at 800 // ms
        })

    //repeatable 反复运行基于时长的动画（例如 tween 或 keyframes），直至达到指定的迭代计数。
    //您可以传递 repeatMode 参数来指定动画是从头开始 (RepeatMode.Restart) 还是从结尾开始 (RepeatMode.Reverse) 重复播放。
    val repeatable by animateFloatAsState(
        targetValue = progress,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        )
    )
    //使用 infiniteRepeatable 的动画不会运行
    val infiniteRepeatable by animateFloatAsState(
        targetValue = progress,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        )
    )
    //snap 是特殊的 AnimationSpec，它会立即将值切换到结束值。您可以指定 delayMillis 来延迟动画播放的开始时间。
    val snap by animateFloatAsState(
        targetValue = progress,
        animationSpec = snap(delayMillis = 50)
    )

    val items = listOf(
        SpecItem("No Animation", progress),
        SpecItem("DampingRatioHighBouncy(0.2f)", springHigh),
        SpecItem("DampingRatioMediumBouncy(0.5f)", springMedium),
        SpecItem("DampingRatioLowBouncy(0.75f)", springLow),
        SpecItem("DampingRatioNoBouncy(2f)", springNo),
        SpecItem("tween", tween),
        SpecItem("keyframe", keyframe),
        SpecItem("repeatable", repeatable),
        SpecItem("infiniteRepeatable", infiniteRepeatable),
        SpecItem("snap", snap),
    )


    Column {
        Slider(value = progress, onValueChange = {
            progress = it
        })
        Spacer(modifier = Modifier.height(Size.small))

        items.forEach { item ->
            Text(text = item.name, modifier = Modifier.padding(top = Size.small))
            LinearProgressIndicator(progress = item.progress, modifier = Modifier.width(200.dp))
        }
    }
}

/**
 * 事件与动画
 */
@Preview
@Composable
fun SimplePointerInput() {
    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    Column {
        Box(modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
            .background(color = Color.Gray)
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        val position = awaitPointerEventScope { awaitFirstDown().position }
                        launch { offset.animateTo(position) }
                    }
                }
            }) {
            Box(modifier = Modifier
                .offset {
                    IntOffset(
                        offset.value.x.roundToInt(),
                        offset.value.y.roundToInt()
                    )
                }
                .size(16.dp)
                .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
            )
        }
    }
}

fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    val offsetX = remember { Animatable(0f) }
    pointerInput(Unit) {
        val decay = splineBasedDecay<Float>(this)
        coroutineScope {
            while (true) {
                // Detect a touch down event.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                val velocityTracker = VelocityTracker()
                // Intercept an ongoing animation (if there's one).
                offsetX.stop()
                awaitPointerEventScope {
                    horizontalDrag(pointerId) { change ->
                        // Update the animation value with touch events.
                        launch {
                            offsetX.snapTo(
                                offsetX.value + change.positionChange().x
                            )
                        }
                        velocityTracker.addPosition(
                            change.uptimeMillis,
                            change.position
                        )
                        logd {
                            "change: ${change.position} ${change.type} ${change.pressed} " +
                                    "${velocityTracker.calculateVelocity()}"
                        }
                    }
                }
                val velocity = velocityTracker.calculateVelocity().x
                val targetOffsetX = decay.calculateTargetValue(
                    offsetX.value,
                    velocity
                )
                // The animation stops when it reaches the bounds.
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )
                logd { "velocity: $velocity targetOffsetX: $targetOffsetX" }
                launch {
                    if (targetOffsetX.absoluteValue <= size.width) {
                        // Not enough velocity; Slide back.
                        offsetX.animateTo(
                            targetValue = 0f,
                            initialVelocity = velocity
                        )
                    } else {
                        // The element was swiped away.
                        offsetX.animateDecay(velocity, decay)
                        onDismissed()
                    }
                }
            }
        }
    }
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun SimpleSwipeToDismiss() {
    val state = rememberDismissState()
    Column {
        SwipeToDismiss(state = state, background = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.Gray)
            ) {}
        }) {
            Text(
                text = "Swipe To Dismiss", modifier = Modifier
                    .fillMaxWidth()
            )
        }
        logd { "state ${state.offset}" }

        Text(text = "My swipe to dismiss", modifier = Modifier.swipeToDismiss {
            logd { "dismiss" }
        })

    }
}

