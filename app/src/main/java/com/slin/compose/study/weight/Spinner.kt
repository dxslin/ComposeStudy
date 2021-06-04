package com.slin.compose.study.weight

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slin.compose.study.R
import com.slin.compose.study.ui.samples.THEMES


/**
 * author: slin
 * date: 2021/4/16
 * description:
 *
 */
@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    list: List<String>,
    defaultText: String = if (list.isNotEmpty()) list[0] else "",
    onTextChange: (String) -> Unit,
    dismiss: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(defaultText) }
    Column(modifier) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = {
                expanded = expanded.not()
            },
            colors = spinnerButtonColors(),
            border = BorderStroke(0.5.dp, MaterialTheme.colors.onSurface),
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = selectedText,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(
                        id = if (expanded) {
                            R.drawable.ic_baseline_arrow_drop_up_24
                        } else {
                            R.drawable.ic_baseline_arrow_drop_down_24
                        }
                    ), contentDescription = "expanded",
                    modifier = Modifier
                        .width(24.dp)
                        .wrapContentHeight()
                )
            }
        }
        DropdownMenu(
            modifier = Modifier.wrapContentHeight(),
            expanded = expanded,
            onDismissRequest = {
                dismiss()
                expanded = false
            }
        ) {
            list.forEach { text ->
                DropdownMenuItem(modifier = Modifier.wrapContentHeight(), onClick = {
                    dismiss()
                    selectedText = text
                    onTextChange(text)
                    expanded = false
                }) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = text, modifier = Modifier.wrapContentWidth(Alignment.Start))
                    }
                }
            }
        }
    }
}

@Composable
private fun spinnerButtonColors(
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        .compositeOver(MaterialTheme.colors.surface),
    disabledContentColor: Color = MaterialTheme.colors.onSurface
        .copy(alpha = ContentAlpha.disabled)
): ButtonColors = SpinnerButtonColors(
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    disabledBackgroundColor = disabledBackgroundColor,
    disabledContentColor = disabledContentColor
)


private class SpinnerButtonColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledContentColor: Color
) : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SpinnerButtonColors

        if (backgroundColor != other.backgroundColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledBackgroundColor != other.disabledBackgroundColor) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledBackgroundColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }

}

@Preview
@Composable
private fun SpannerDemo() {
    val themeValue = remember { mutableStateOf(THEMES[0]) }
    Spinner(
        list = THEMES,
        modifier = Modifier
            .requiredWidth(IntrinsicSize.Min)
            .wrapContentHeight(Alignment.CenterVertically),
        onTextChange = {
            themeValue.value = it
        }
    )

}
