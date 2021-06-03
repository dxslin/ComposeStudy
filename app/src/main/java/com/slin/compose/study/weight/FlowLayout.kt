package com.slin.compose.study.weight

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.slin.compose.study.ui.samples.texts

/**
 * author: slin
 * date: 2021/6/3
 * description: 流式布局
 *
 */

internal class Row(val width: Int, val height: Int, val placeables: List<Placeable>)

@Composable
fun FlowLayout(
    modifier: Modifier,
    singleLine: Boolean = false,
    arrangement: FlowArrangement = FlowArrangement.Packed(Alignment.Start),
    childVerticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable () -> Unit
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        var contentWidth = 0
        var contentHeight = 0
        val rows = mutableListOf<Row>()
        val maxWidth = constraints.maxWidth

        val childConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val placeables = measurables.map { measurable ->
            measurable.measure(childConstraints)
        }

        var rowWidth = 0
        var rowHeight = 0
        var rowPlaceables = mutableListOf<Placeable>()

        for (placeable in placeables) {
            val curRowWidth = rowWidth + placeable.width
            if (curRowWidth > maxWidth) {
                if (singleLine) break
                contentWidth = maxOf(contentWidth, rowWidth)
                contentHeight += rowHeight
                rows.add(Row(rowWidth, rowHeight, rowPlaceables))
                rowWidth = placeable.width
                rowHeight = placeable.height
                rowPlaceables = mutableListOf(placeable)
            } else {
                rowWidth += placeable.width
                rowHeight = maxOf(rowHeight, placeable.height)
                rowPlaceables.add(placeable)
            }
        }
        rows.add(Row(rowWidth, rowHeight, rowPlaceables))
        contentWidth = maxOf(contentWidth, rowWidth)
        contentHeight += rowHeight

        val width = if (arrangement is FlowArrangement.Packed) maxOf(
            contentWidth,
            constraints.minWidth
        ) else constraints.maxWidth

        layout(
            width = width,
            height = maxOf(contentHeight, constraints.minHeight)
        ) {
            var yPos = 0
            rows.forEach { row ->
                with(arrangement) {
                    horizontalPlace(width, row) { placeable ->
                        yPos - childVerticalAlignment.align(row.height - placeable.height, 0)
                    }
                    yPos += row.height
                }
            }
        }
    }
}

sealed class FlowArrangement {
    object Spread : FlowArrangement() {
        override fun Placeable.PlacementScope.horizontalPlace(
            availableWidth: Int,
            row: Row,
            placeChildVertical: (Placeable) -> Int,
        ) {
            val space = if (row.placeables.isNotEmpty()) {
                (availableWidth - row.width) / row.placeables.size
            } else 0
            var x = space / 2
            for (placeable in row.placeables) {
                placeable.placeRelative(x = x, y = placeChildVertical(placeable))
                x += placeable.width + space
            }
        }
    }

    object SpreadInside : FlowArrangement() {
        override fun Placeable.PlacementScope.horizontalPlace(
            availableWidth: Int,
            row: Row,
            placeChildVertical: (Placeable) -> Int
        ) {
            val space = if (row.placeables.size > 1) {
                (availableWidth - row.width) / (row.placeables.size - 1)
            } else 0
            var x = space / 2
            for (placeable in row.placeables) {
                placeable.placeRelative(x = x, y = placeChildVertical(placeable))
                x += placeable.width + space
            }
        }
    }

    class Packed(private val childHorizontalAlignment: Alignment.Horizontal) : FlowArrangement() {
        override fun Placeable.PlacementScope.horizontalPlace(
            availableWidth: Int,
            row: Row,
            placeChildVertical: (Placeable) -> Int
        ) {
            var x = childHorizontalAlignment.align(
                size = availableWidth - row.width,
                space = 0,
                layoutDirection = LayoutDirection.Ltr
            )
            row.placeables.forEach { placeable ->
                placeable.placeRelative(x = x, y = placeChildVertical(placeable))
                x += placeable.width
            }
        }
    }

    internal open fun Placeable.PlacementScope.horizontalPlace(
        availableWidth: Int,
        row: Row,
        placeChildVertical: (Placeable) -> Int,
    ) {
        var x = 0
        row.placeables.forEach { placeable ->
            placeable.placeRelative(x = x, y = placeChildVertical(placeable))
            x += placeable.width
        }
    }
}


//@Composable
//fun FlowLayout(
//    modifier: Modifier,
//    singleLine: Boolean = false,
//    content: @Composable () -> Unit
//) {
//    Layout(content = content, modifier = modifier) { measurables, constraints ->
//        val maxWidth = constraints.maxWidth
//        var maxHeight = 0
//
//        val childConstraints = constraints.copy(minWidth = 0, minHeight = 0)
//
//        var line = 0
//        var curRowWidth = 0
//        var curRowMaxHeight = 0
//
//        val placeables = measurables.map { measurable ->
//            measurable.measure(childConstraints)
//        }
//        val rowPlaceables = placeables.groupBy { placeable ->
//            val rowWidth = curRowWidth + placeable.width
//            if (singleLine) {
//                if (rowWidth > maxWidth) {
//                    line = -1
//                } else {
//                    curRowWidth = rowWidth
//                    if (placeable.height > curRowMaxHeight) {
//                        curRowMaxHeight = placeable.height
//                        maxHeight = curRowMaxHeight
//                    }
//                }
//
//            } else {
//                if (rowWidth > maxWidth) {
//                    maxHeight += curRowMaxHeight
//                    curRowMaxHeight = placeable.height
//
//                    curRowWidth = placeable.width
//                    line++
//                } else {
//                    curRowWidth = rowWidth
//                    curRowMaxHeight = maxOf(curRowMaxHeight, placeable.height)
//
//                }
//                //最后一个的时候如果不是行末尾，需要加上最后一行的高度
//                if (placeable == placeables.last()) {
//                    maxHeight += curRowMaxHeight
//                }
//            }
//            line
//        }
//
//
//        layout(width = maxWidth, height = maxHeight) {
//            var yPos = 0
//            rowPlaceables.forEach { (line, linePlaceables) ->
//                if (line >= 0) {
//                    var xPos = 0
//                    var height = 0
//                    linePlaceables.forEach { placeable ->
//                        placeable.placeRelative(x = xPos, y = yPos)
//                        xPos += placeable.width
//                        height = maxOf(height, placeable.height)
//                    }
//                    yPos += height
//
//                }
//            }
//        }
//    }
//}


@Preview
@Composable
private fun FlowLayoutTest() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
//        FlowLayout(modifier = Modifier.fillMaxWidth(), singleLine = true, content = texts)
        Row(modifier = Modifier.fillMaxWidth()) {
            FlowLayout(modifier = Modifier.weight(0.7f), content = texts)
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
