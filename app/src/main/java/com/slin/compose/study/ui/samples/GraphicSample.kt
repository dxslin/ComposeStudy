package com.slin.compose.study.ui.samples

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.slin.compose.study.R
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import java.lang.Float.min

/**
 * author: slin
 * date: 2021/4/22
 * description: draw api试用
 *
 */
@Composable
fun GraphicSample() {
    val testItems = listOf(
        LayoutItem("1. SimpleDraw") { SimpleDraw() },
        LayoutItem("2. Clock") { Clock() },
    )

    ScaffoldWithCsAppBar(title = "CanvasSample") { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(innerPadding),
            contentPadding = ComposeStudyTheme.paddings.medium
        ) {
            items(testItems) {
                TestItem(item = it)
            }
        }
    }
}

@Preview
@Composable
fun SimpleDraw() {
    val context = LocalContext.current
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_cartoon_cat)
    val imageBitmap = bitmap.asImageBitmap()


    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(Color(0xFFFFF8E1))
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val lineStrokeWidth = 5f

        inset(32f, 32f) {
            //inset里面的size/center等数据与外层不一样，innerSize = outSize - inset
            drawRect(color = Color(0xFFEDE7F6), size = size)
        }

        drawLine(
            color = Color.Red,
            start = Offset.Zero,
            end = Offset(canvasWidth, canvasHeight),
            strokeWidth = lineStrokeWidth
        )
        drawLine(
            color = Color.Red,
            start = Offset(0f, canvasHeight),
            end = Offset(canvasWidth, 0f),
            strokeWidth = lineStrokeWidth
        )

        val boxSize = Size(canvasWidth / 6, canvasHeight / 6)
        drawRect(
            //绘制渐变色
            brush = Brush.linearGradient(
                listOf(Color(0xFF512DA8), Color(0xFF7B1FA2), Color(0xFFC2185B)),
                start = Offset.Zero,
                end = Offset(boxSize.width, boxSize.height),
                tileMode = TileMode.Clamp
            ),
            topLeft = Offset.Zero,
            size = boxSize,
        )
        drawRect(
            brush = Brush.linearGradient(
                listOf(Color(0xFF512DA8), Color(0xFF7B1FA2), Color(0xFFC2185B)),
                start = Offset(boxSize.width, boxSize.height),
                end = Offset.Zero,
                tileMode = TileMode.Mirror
            ),
            topLeft = Offset(canvasWidth - boxSize.width, 0f),
            size = boxSize,
        )

        drawCircle(
            color = Color(0xFFD32F2F),
            radius = min(canvasWidth, canvasHeight) / 6,
            center = Offset(canvasWidth / 2, canvasHeight / 2),
        )
        val imageSize = IntSize((canvasWidth / 12).toInt(), (canvasWidth / 12).toInt())
        translate(top = 8f) {
            drawImage(
                image = imageBitmap,
                dstOffset = IntOffset(
                    (center.x - imageSize.width / 2).toInt(),
                    (center.y - imageSize.height / 2).toInt()
                ),
                dstSize = imageSize
            )
        }

        (-3..3).forEach {
            //可以应用旋转(rotate)、位移(translate)、缩放(scale)等常用图像变化
            rotate(it * 20f) {
                drawLine(
                    color = Color(0xFF43A047),
                    start = Offset(canvasWidth / 2, canvasHeight / 2),
                    end = Offset(canvasWidth / 2, canvasHeight / 2 - canvasHeight / 4),
                    strokeWidth = 8f,
                    cap = StrokeCap.Round,
                )
            }
        }

        (-2..2).forEach {
            withTransform({
                translate(left = it * 60f)
            }) {
                drawCircle(
                    color = Color(0xFF039BE5),
                    radius = 10f,
                    center = Offset(canvasWidth / 2, canvasHeight / 2 + canvasHeight / 3)
                )
            }

        }

    }
}


@Composable
fun Clock() {

}
