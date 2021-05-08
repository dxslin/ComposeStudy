package com.slin.compose.study.ui.samples

import android.graphics.BitmapFactory
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.slin.compose.study.R
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.Float.min
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

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
        LayoutItem("2. Clock") { SimpleClock() },
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

//@Preview
@Composable
fun SimpleClock() {
    val calendar = remember { mutableStateOf(Calendar.getInstance()) }

    Row {
        Clock(
            modifier = Modifier.size(200.dp, 200.dp),
            second = calendar.value.get(Calendar.SECOND),
            minute = calendar.value.get(Calendar.MINUTE),
            hour = calendar.value.get(Calendar.HOUR_OF_DAY),
            fontSize = 10.sp
        )

        Clock(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(100.dp, 100.dp),
            second = calendar.value.get(Calendar.SECOND),
            minute = calendar.value.get(Calendar.MINUTE),
            hour = calendar.value.get(Calendar.HOUR_OF_DAY),
            fontSize = 8.sp
        )

    }

    LaunchedEffect(calendar.value) {
        launch {
            while (isActive) {
                calendar.value = Calendar.getInstance()
//                logd { "clock: ${calendar.value.time}" }
                delay(1000)
            }
        }
    }

}

@Preview
@Composable
fun Clock(
    modifier: Modifier = Modifier.size(100.dp, 100.dp),
    calendar: Calendar = Calendar.getInstance(),
    second: Int = calendar.get(Calendar.SECOND),
    minute: Int = calendar.get(Calendar.MINUTE),
    hour: Int = calendar.get(Calendar.HOUR_OF_DAY),
    fontSize: TextUnit = 8.sp,
) {

//    val dialPointerColor =
//        listOf(Color(0xFFE53935), Color(0xFF00897B), Color(0xFF8E24AA), Color(0xFF1E88E5))
    val dialPointerColor =
        listOf(Color(0xFF43A047), Color(0xFFD81B51), Color(0xFFFB8C00), Color(0xFF00ACC1))

    val computeCirclePoint: (center: Offset, radius: Float, angle: Float) -> Offset =
        { center: Offset, radius: Float, angle: Float ->
            val ang = (angle / 180 * Math.PI).toFloat()
            Offset(center.x + radius * cos(ang), center.y + radius * sin(ang))
        }


    Canvas(
        modifier = modifier
            .padding(5.dp)
            .defaultMinSize(50.dp, 50.dp)
    ) {
        val diameter = min(size.width, size.height)
        val radius = diameter / 2f

        val brush = Brush.sweepGradient(
            0f to dialPointerColor[0],
            0.25f to dialPointerColor[1],
            0.5f to dialPointerColor[2],
            0.75f to dialPointerColor[3],
            1f to dialPointerColor[0],
            center = center
        )

        //绘制表盘外圆
        drawCircle(
            brush = brush,
            radius = radius,
            center = center,
            style = Stroke(width = 2f)
        )

        val primaryDailLength = radius / 5
        val minorDialLength = primaryDailLength / 2

        for (i in 0..35) {
            if (i % 3 == 0) {
                drawLine(
                    brush = brush,
                    start = computeCirclePoint(center, radius, 10f * i),
                    end = computeCirclePoint(center, radius - primaryDailLength, 10f * i),
                    strokeWidth = 6f,
                )

            } else {
                drawLine(
                    brush = brush,
                    start = computeCirclePoint(center, radius, 10f * i),
                    end = computeCirclePoint(center, radius - minorDialLength, 10f * i),
                    strokeWidth = 4f,
                )
            }
        }

        val nativeCanvas = drawContext.canvas.nativeCanvas
        //绘制文字
        val paint = Paint()
        paint.color = android.graphics.Color.BLACK
        paint.textSize = fontSize.toPx()

        val fontHeight = paint.textSize
        val fontDailMargin = 5
        nativeCanvas.drawText(
            "3",
            center.x + radius - primaryDailLength - paint.measureText("3") - fontDailMargin,
            center.y + fontHeight / 2 - paint.fontMetrics.descent,
            paint
        )
        nativeCanvas.drawText(
            "6",
            center.x - paint.measureText("6") / 2,
            center.y + radius - primaryDailLength - paint.fontMetrics.descent - fontDailMargin,
            paint
        )
        nativeCanvas.drawText(
            "9",
            center.x - radius + primaryDailLength + fontDailMargin,
            center.y + fontHeight / 2 - paint.fontMetrics.descent,
            paint
        )
        nativeCanvas.drawText(
            "12",
            center.x - paint.measureText("12") / 2,
            center.y - radius + primaryDailLength + fontHeight - paint.fontMetrics.descent + fontDailMargin,
            paint
        )

        val text = String.format("%02d:%02d:%02d", hour, minute, second)
        nativeCanvas.drawText(
            text,
            center.x - paint.measureText(text) / 2,
            center.y + radius * 0.55f - paint.fontMetrics.descent,
            paint
        )


        val centerCircleSize = radius / 15
        //秒针
        var angle = 6f * second - 90
        drawLine(
            brush = brush,
            start = computeCirclePoint(center, centerCircleSize, angle),
            end = computeCirclePoint(center, radius - primaryDailLength * 2, angle),
            strokeWidth = 4f,
            cap = StrokeCap.Butt,
            alpha = 0.7f
        )
        //分针
        angle = 6f * (minute + second / 60f) - 90
        drawLine(
            brush = brush,
            start = computeCirclePoint(center, centerCircleSize, angle),
            end = computeCirclePoint(center, radius - primaryDailLength * 2.5f, angle),
            strokeWidth = 6f,
            cap = StrokeCap.Round,
            alpha = 0.8f
        )
        //时针
        angle = 30f * (hour + minute / 60f) - 90
        drawLine(
            brush = brush,
            start = computeCirclePoint(center, centerCircleSize, angle),
            end = computeCirclePoint(center, radius - primaryDailLength * 3.2f, angle),
            strokeWidth = 10f,
            cap = StrokeCap.Square,
            alpha = 0.9f
        )
        drawCircle(brush, centerCircleSize, center, style = Fill)


    }


}




