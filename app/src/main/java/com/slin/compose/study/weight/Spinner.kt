package com.slin.compose.study.weight

import android.widget.Spinner
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


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
    expanded: Boolean = false,
    dismiss: () -> Unit = {},
    onItemClick: (index: Int, text: String) -> Unit,
) {
    DropdownMenu(modifier = modifier, expanded = expanded, onDismissRequest = dismiss) {
        list.forEachIndexed { index, text ->
            DropdownMenuItem(onClick = {
                dismiss()
                onItemClick(index, text)
            }) {
                Text(text = text, modifier = Modifier.wrapContentWidth().align(Alignment.Start))
            }
        }

    }


}