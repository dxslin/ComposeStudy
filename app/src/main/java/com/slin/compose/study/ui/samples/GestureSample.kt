package com.slin.compose.study.ui.samples

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import java.lang.Float.max
import java.lang.Float.min
import kotlin.math.roundToInt

/**
 * author: slin
 * date: 2021/5/21
 * description: 手势事件
 *
 */
@Preview
@Composable
fun GestureSample() {
    val testItems = listOf<LayoutItem>(
        LayoutItem("1. SimpleClick") { SimpleClick() },
        LayoutItem("2. SimpleScrollable") { SimpleScrollable() },
        //嵌套滚动这个不能加载到滚动的Column中
//        LayoutItem("3. SimpleNestScroll") { SimpleNestScroll() },
        LayoutItem("4. SimpleDrag") { SimpleDrag() },
        LayoutItem("5. SimpleDrag2") { SimpleDrag2() },
    )


    ScaffoldWithCsAppBar(title = "GestureSample") { innerPadding ->
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = Size.medium),
//            contentPadding = PaddingValues(bottom = Size.medium)
        ) {
//            items(testItems) {
//                TestItem(item = it)
//            }
            testItems.forEach {
                TestItem(item = it)
            }
        }

    }
}

/**
 * 点击事件，单击、双击、长按
 */
@Preview
@Composable
private fun SimpleClick() {
    var count by remember { mutableStateOf(0) }
    Text(
        text = count.toString(),
        modifier = Modifier
            .clickable { count++ }
            .background(color = Color.Gray)
            .padding(ComposeStudyTheme.paddings.small))
    var text by remember { mutableStateOf("Click") }
    Text(
        text = text,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { text = "OnPress" },
                    onDoubleTap = { text = "OnDoubleTap" },
                    onLongPress = { text = "OnLongPress" },
                    onTap = { text = "OnTap" })
            }
            .padding(top = Size.small)
            .background(color = Color.Gray)
            .padding(ComposeStudyTheme.paddings.small))

}

/**
 * 滚动
 */
@Preview
@Composable
private fun SimpleScrollable() {
    var offset by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.Gray)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                })
    ) {
        Text(text = "offset: $offset")
    }
}

/**
 * 嵌套滚动
 * 当滑动子控件时，子控件先滚动直到不可滑动时再滚动父控件
 */
@Preview
@Composable
private fun SimpleNestScroll() {
    val gradient = Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxHeight(0.8f)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SimpleDrag() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.Gray)
    ) {
        Text(text = "Drag me", modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .draggable(
                rememberDraggableState {
                    offsetX += it
                }, orientation = Orientation.Horizontal
            )
            .draggable(rememberDraggableState {
                offsetY += it
            }, orientation = Orientation.Vertical)
        )
    }
}

@Preview
@Composable
fun SimpleDrag2() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.Gray)
    ) {
        Text(text = "Drag me", modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y

                    offsetX = max(offsetX, 0f)
                    offsetX = min(offsetX, size.width.toFloat())

                    offsetY = max(offsetY, 0f)
                    offsetY = min(offsetY, size.height.toFloat())

                }
            }
        )
    }
}




