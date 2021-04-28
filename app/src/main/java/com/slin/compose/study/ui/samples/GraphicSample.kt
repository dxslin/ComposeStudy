package com.slin.compose.study.ui.samples

import android.graphics.BitmapFactory
import android.graphics.Path
import android.graphics.PathMeasure
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.slin.compose.study.R
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
            contentPadding = PaddingValues(bottom = com.slin.compose.study.ui.theme.Size.medium)
        ) {
            items(testItems) {
                TestItem(item = it)
            }
        }
    }
}

/**
 * 试用Canvas的各种api
 */
@Preview
@Composable
fun SimpleDraw() {
    val context = LocalContext.current
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_cartoon_cat)
    val imageBitmap = bitmap.asImageBitmap()
    val dialPointerColor =
        listOf(Color(0xFFE53935), Color(0xFF00897B), Color(0xFF8E24AA), Color(0xFF1E88E5))

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(Color(0xFFFFF8E1))
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val lineStrokeWidth = 5f
        val radius = min(canvasWidth, canvasHeight) / 6

        //各种渐变测试
        val brush = Brush.radialGradient(
            dialPointerColor,
            center = center,
            radius = radius,
            tileMode = TileMode.Repeated
        )

//        val brush = Brush.linearGradient(
//            dialPointerColor,
//            start = Offset(center.x - radius, center.y - radius),
//            end = Offset(center.x + radius, center.y + radius),
//            tileMode = TileMode.Mirror
//        )

//        val brush = Brush.horizontalGradient(
//            dialPointerColor,
//            startX = 0f,
//            endX = radius,
//            tileMode = TileMode.Mirror
//        )

//        val brush = Brush.sweepGradient(
//            0f to dialPointerColor[0],
//            0.25f to dialPointerColor[1],
//            0.5f to dialPointerColor[2],
//            0.75f to dialPointerColor[3],
//            1f to dialPointerColor[0],
//            center = center
//        )

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
            brush = brush,
            radius = radius,
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

@Preview
@Composable
fun Clock(modifier: Modifier = Modifier.size(100.dp, 100.dp)) {

//    val dialPointerColor = listOf(Color(0xFFE53935), Color(0xFFD81B60), Color(0xFF8E24AA), Color(0xFFF4511E))
    val dialPointerColor =
        listOf(Color(0xFFE53935), Color(0xFF00897B), Color(0xFF8E24AA), Color(0xFF1E88E5))

    Canvas(
        modifier = modifier
            .padding(5.dp)
            .defaultMinSize(50.dp, 50.dp)
    ) {
        val diameter = min(size.width, size.height)
        val radius = diameter / 2f
        val perimeter = (Math.PI * diameter).toFloat()

//        val brush = Brush.radialGradient(dialPointerColor, center = center, radius =  radius, tileMode = TileMode.Repeated)

//        val brush = Brush.linearGradient(
//            dialPointerColor,
//            start = Offset(0f, 0f),
//            end = Offset(size.width / 2, size.height / 2),
//            tileMode = TileMode.Mirror
//        )

//        val brush = Brush.horizontalGradient(
//            dialPointerColor,
//            startX = 0f,
//            endX = radius,
//            tileMode = TileMode.Mirror
//        )

        val brush = Brush.sweepGradient(
            0f to dialPointerColor[0],
            0.25f to dialPointerColor[1],
            0.5f to dialPointerColor[2],
            0.75f to dialPointerColor[3],
            1f to dialPointerColor[0],
            center = center
        )

//        drawRect(brush = brush, )

        //绘制表盘外圆
        drawCircle(
            brush = brush,
            radius = radius,
            center = center,
            style = Stroke(width = 2f)
        )


        val primaryDailLength = radius / 5
        val minorDialLength = primaryDailLength / 2

        val outPath = Path()
        outPath.addCircle(center.x, center.y, radius, Path.Direction.CW)
        val outMeasure = PathMeasure(outPath, true)

        val midPath = Path()
        midPath.addCircle(center.x, center.y, radius - minorDialLength, Path.Direction.CW)
        val midMeasure = PathMeasure(midPath, true)

        val innPath = Path()
        innPath.addCircle(center.x, center.y, radius - primaryDailLength, Path.Direction.CW)
        val innMeasure = PathMeasure(innPath, true)

        val point = FloatArray(2)
        for (i in 0..35) {
            if (i % 3 == 0) {
                outMeasure.getPosTan(outMeasure.length / 36 * i, point, null)
                val start = Offset(point[0], point[1])
                innMeasure.getPosTan(innMeasure.length / 36 * i, point, null)
                val end = Offset(point[0], point[1])
                drawLine(brush = brush, start = start, end = end, strokeWidth = 5f)
            } else {
                outMeasure.getPosTan(outMeasure.length / 36 * i, point, null)
                val start = Offset(point[0], point[1])
                midMeasure.getPosTan(midMeasure.length / 36 * i, point, null)
                val end = Offset(point[0], point[1])
                drawLine(brush = brush, start = start, end = end, strokeWidth = 3f)
            }
        }


    }


}
