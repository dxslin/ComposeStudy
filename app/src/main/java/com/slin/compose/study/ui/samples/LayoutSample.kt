package com.slin.compose.study.ui.samples

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.accompanist.insets.navigationBarsPadding
import com.slin.compose.study.R
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import com.slin.compose.study.weight.*
import com.slin.core.logger.logd
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * author: slin
 * date: 2021/3/12
 * description:常用布局
 *
 */

class LayoutItem(val title: String, val content: @Composable() () -> Unit)

@Composable
fun LayoutSample() {

    val layoutItems = listOf(
        LayoutItem("1. RowTest") { RowTest() },
        LayoutItem("2. ColumnTest") { ColumnTest() },
        LayoutItem("3. ConstraintTest") { ConstraintTest() },
        LayoutItem("4. BoxTest") { BoxTest() },
        LayoutItem("4. RhombusTest") { RhombusTest() },
        LayoutItem("5. FlowLayoutTest") { FlowLayoutTest() },
        LayoutItem("6. ImageTest") { ImageTest() },

        )

    MultiTestPage(title = "LayoutSample", testItems = layoutItems)
}

@Composable
fun MultiTestPage(title: String, testItems: List<LayoutItem>) {
    ScaffoldWithCsAppBar(title = title) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = Size.medium)
        ) {
            items(testItems) { item ->
                TestItem(item = item)
            }
        }

    }
}


@Composable
fun TestItem(item: LayoutItem) {
    Card(
        elevation = 5.dp, modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ComposeStudyTheme.paddings.medium)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            item.content()
        }
    }
}

@Preview
@Composable
private fun RowTest() {
    val context = LocalContext.current
    var text by remember { mutableStateOf("row 2") }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "row 1",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.button
        )

        Text(text = text, Modifier.clickable {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            text = "text clicked"
        })
        Text(
            text = "background clip", modifier = Modifier
                .padding(start = 16.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .background(Color.Gray)
                .padding(4.dp)
        )

    }
}

//@Preview
@Composable
private fun ColumnTest() {
    val context = LocalContext.current
    val scrollState = ScrollState(0)
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
            .height(200.dp)
    ) {
        item {
            Column() {
                Text(
                    text = "Column h2",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = "Column h3",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.h3
                )
                Text(
                    text = "Column h4",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = "Column h5",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = "Column h6",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "Column subtitle1",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = "Column subtitle2",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = "Column body1",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Column body2",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = "Column caption",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "Column overline",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.overline
                )
                Text(
                    text = "Column button",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.button
                )

                Button(onClick = {
                    Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Button")
                }
            }
        }

        scope.launch {
            scrollState.interactionSource.interactions.collect {
                logd { "scroll state: $it" }
            }

        }
    }


}

/**
 * ConstraintLayout 使用
 * 1. 首先使用createRefs创建id引用变量，然后再constrainAs方法中创建约束
 *
 */
@Preview
@Composable
private fun ConstraintTest() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (startRef, topRef, endRef, bottomRef, centerRef) = createRefs()
        Text(text = "top", modifier = Modifier.constrainAs(topRef) {
            top.linkTo(parent.top)
            centerHorizontallyTo(parent)
        })

        Text(text = "start", modifier = Modifier
            .padding(horizontal = 8.dp)
            .constrainAs(startRef) {
                top.linkTo(topRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(centerRef.start)
            }
            .wrapContentWidth(Alignment.Start)
            .background(Color.Gray))
        Text(text = "center",
            modifier = Modifier
                .constrainAs(centerRef) {
                    top.linkTo(topRef.bottom)
                    start.linkTo(startRef.end)
                    end.linkTo(endRef.start)
                }
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp))
        Text(text = "end", modifier = Modifier
            .constrainAs(endRef) {
                top.linkTo(topRef.bottom)
                start.linkTo(centerRef.end)
                end.linkTo(parent.end)
            }
            .wrapContentWidth(Alignment.End)
            .background(Color.Gray))
        Text(text = "bottom", modifier = Modifier.constrainAs(bottomRef) {
            top.linkTo(centerRef.bottom)
            centerHorizontallyTo(centerRef)
        })
    }
}

/**
 * Box使用
 * 1. background、padding这些属性可以多次使用，他们在不同的位置，会使得效果不一致<p>
 * 2. fillMaxSize会和父布局一起填充满所有可用空间;  <p>
 *      matchParentSize 只会填充满当前父布局的可用空间，不会影响父布局的大小安排，但是matchParentSize仅在BoxScope可以<p>
 *      requiredSize 可以突破父布局大小限制，给出一个固定大小 <p>
 * 3. 如果子控件需要依赖子控件测量数据，比如此例中分割线需要根据Text的具体高度调整自身高度，<p>
 *     如果直接使用`fillMaxHeight`方法会把父布局无线放大，<p>
 *     这时就还需要再父布局中设置`height(IntrinsicSize.Min/Max)`来限制父布局
 *
 */
@Preview
@Composable
private fun BoxTest() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .background(Color.Black)
                .padding(10.dp)
                .background(Color.Green)
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            )
        }

        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(100.dp, 100.dp)
                .background(Color.Gray)
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Magenta)
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(100.dp, 100.dp)
                .background(Color.Cyan)
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(110.dp, 50.dp)
                    .background(Color.Red)
            )
        }

        Row(modifier = Modifier.size(200.dp, 60.dp)) {
            Box(
                modifier = Modifier
                    .weight(0.614f)
                    .fillMaxHeight()
                    .background(Color.Magenta)
            )
            Box(
                modifier = Modifier
                    .weight(1 - 0.614f)
                    .fillMaxHeight()
                    .background(Color.Green)
            )
        }

        TwoTexts(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(IntrinsicSize.Min),
            text1 = "text1",
            text2 = "text2"
        )
        TwoTexts(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(IntrinsicSize.Max),
            text1 = "text3",
            text2 = "text4"
        )
    }
}

//@Preview
@Composable
private fun TwoTexts(
    modifier: Modifier = Modifier,
    text1: String = "text1",
    text2: String = "text2"
) {
    Row(modifier = modifier.background(color = Color.DarkGray)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentSize(Alignment.CenterStart),
            text = text1
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.Black
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .padding(end = 4.dp)
                .wrapContentSize(Alignment.CenterEnd),
            text = text2
        )
    }
}

val texts: @Composable () -> Unit = {
    Text(
        text = "text1", modifier = Modifier
            .height(28.dp)
            .padding(horizontal = 4.dp)
    )

    Text(text = "text2", modifier = Modifier.padding(horizontal = 4.dp))
    Text(text = "text3", modifier = Modifier.padding(horizontal = 4.dp))

    Text(text = "text4", modifier = Modifier.padding(horizontal = 4.dp))
    Text(text = "text5", modifier = Modifier.padding(horizontal = 4.dp))
    Text(text = "text6", modifier = Modifier.padding(horizontal = 4.dp))

    Text(text = "text7", modifier = Modifier.padding(horizontal = 4.dp))
    Text(
        text = "text8", modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(40.dp)
    )

    Text(text = "text9", modifier = Modifier.padding(horizontal = 4.dp))
}

@Preview
@Composable
private fun RhombusTest() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Rhombus(centerSize = 3, modifier = Modifier.weight(0.7f), content = texts)

        Rhombus(
            centerSize = 2, modifier = Modifier
                .padding(start = 16.dp)
                .weight(0.3f)
        ) {
            Text(text = "text1", modifier = Modifier.padding(horizontal = 4.dp))

            Text(text = "text2", modifier = Modifier.padding(horizontal = 4.dp))
            Text(text = "text3", modifier = Modifier.padding(horizontal = 4.dp))

            Text(text = "text4", modifier = Modifier.padding(horizontal = 4.dp))
        }

    }
}

@Preview
@Composable
private fun FlowLayoutTest() {
    var arrangement by remember { mutableStateOf<FlowArrangement>(FlowArrangement.Spread) }
    var childVerticalAlignment by remember { mutableStateOf(Alignment.Top) }

    Column {
        Row {
            Spinner(list = listOf("Spread", "SpreadInside", "Packed"), onTextChange = {
                arrangement = when (it) {
                    "Spread" -> FlowArrangement.Spread
                    "SpreadInside" -> FlowArrangement.SpreadInside
                    "Packed" -> FlowArrangement.Packed(Alignment.Start)
                    else -> FlowArrangement.Spread
                }
            }, modifier = Modifier.width(140.dp))
            Spinner(
                list = listOf("Top", "CenterVertically", "Bottom"), onTextChange = {
                    childVerticalAlignment = when (it) {
                        "Top" -> Alignment.Top
                        "CenterVertically" -> Alignment.CenterVertically
                        "Bottom" -> Alignment.Bottom
                        else -> Alignment.Top
                    }
                }, modifier = Modifier
                    .padding(start = 16.dp)
                    .width(140.dp)
            )
        }

        Text(text = "Single line", style = ComposeStudyTheme.typography.subtitle2)
        FlowLayout(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            arrangement = arrangement,
            childVerticalAlignment = childVerticalAlignment,
            content = texts
        )
        Text(text = "Flow", style = ComposeStudyTheme.typography.subtitle2)
        Row(modifier = Modifier.fillMaxWidth()) {
            FlowLayout(
                modifier = Modifier.weight(0.7f),
                arrangement = arrangement,
                childVerticalAlignment = childVerticalAlignment, content = texts
            )
            FlowLayout(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(0.3f)
            ) {
                Text(text = "text1", modifier = Modifier.padding(horizontal = 4.dp))

                Text(text = "text2", modifier = Modifier.padding(horizontal = 4.dp))
                Text(text = "text3", modifier = Modifier.padding(horizontal = 4.dp))

                Text(text = "text4", modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}

/**
 * 实现自定义布局，下面示例实现了一个菱形形状的布局方式
 * 1. 使用Layout实现自定义布局，其measurePolicy参数为布局实现方式（其layout方法）
 *
 */
@Composable
private fun Rhombus(
    modifier: Modifier = Modifier,
    centerSize: Int,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        //子控件数量太少不足以绘制一个正常的金字塔
        val centerOffset = (centerSize + 1) * centerSize / 2;
        check(measurables.size >= centerOffset) { "centerSize太大，而子布局数量不足" }

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints = constraints.copy(minWidth = 0, minHeight = 0))
        }

        var line = 1
        var lineCount = 1
        var preTotalCount = lineCount
        var height = 0
        var lineHeight = 0

        var index = 0
        //将子控件按行分组
        val linePlaceables = placeables.groupBy { placeable ->
            val key = line
            if (placeable.height > lineHeight) {
                lineHeight = placeable.height
            }

            if (++index == preTotalCount) {
                height += lineHeight
                lineHeight = 0

                if (preTotalCount < centerOffset) {
                    lineCount++
                } else {
                    lineCount--
                }
                preTotalCount += lineCount
                line++
            } else if (index == placeables.size) {
                //如果最后一个不是行最后一个，还是需要加上一个行高
                height += lineHeight
            }
            key
        }


        layout(constraints.maxWidth, height = height) {
            var yPosition = 0

            linePlaceables.forEach { (_, linePlaceable) ->
                var lineWidth = 0
                linePlaceable.forEach { placeable ->
                    lineWidth += placeable.width
                }
                var x = (constraints.maxWidth - lineWidth) / 2
                var height = 0
                linePlaceable.forEachIndexed { index, placeable ->
                    placeable.place(x, yPosition)
                    x += placeable.width
                    height = maxOf(height, placeable.height)
                }
                yPosition += height
            }

        }
    }

}

@Composable
private fun ImageTest() {
    val context = LocalContext.current

    val borderImage: @Composable (content: @Composable () -> Unit) -> Unit = { content ->
        Surface(border = BorderStroke(1.dp, Color.Red)) {
            content()
        }
    }

    Column() {
        NetworkImage(
            url = "https://images.unsplash.com/photo-1629206896310-68433de7ded1?auto=format&fit=crop&w=500&q=60",
            contentDescription = "000",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
        )
        borderImage {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.img_cartoon_1),
                contentDescription = "只支持BitmapDrawable"
            )
        }
        borderImage {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            painter = painterResource(id = R.drawable.img_cartoon_1),
                contentDescription = "图片或者Vector"
            )
        }
        borderImage {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_star_24),
                contentDescription = "Vector"
            )
        }
        borderImage {
            //如果是Shape的话，需要转化为Bitmap
            Image(
                bitmap = ContextCompat.getDrawable(context, R.drawable.shape_round_rect_primary)
                !!.toBitmap(100, 60).asImageBitmap(),
                contentDescription = "Shape"
            )
        }
        borderImage {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .backgroundRes(id = R.drawable.img_cartoon_1)
            ) {
                Text(text = "Image Background")

            }
        }


    }
}
