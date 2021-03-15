package com.slin.compose.study.ui.samples

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slin.compose.study.R
import com.slin.compose.study.ui.theme.Padding
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsPadding


/**
 * author: slin
 * date: 2021/3/12
 * description:
 *
 */

class LayoutItem(val title: String, val content: @Composable() () -> Unit)

@Preview
@Composable
fun LayoutSample() {
    val scaffoldState = rememberScaffoldState()

    val layoutItems = listOf(
        LayoutItem("1. RowTest") { RowTest() },
        LayoutItem("2. ColumnTest") { ColumnTest() }
    )

    Scaffold(scaffoldState = scaffoldState,
        topBar = { com.slin.compose.study.ui.theme.AppBar() }) {
        LazyColumn {
            items(layoutItems) { item ->
                TestItem(item = item)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}


@Composable
fun TestItem(item: LayoutItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
    ) {
        Text(text = item.title, style = MaterialTheme.typography.h6)
        item.content()
    }
}

@Composable
fun RowTest() {
    val context = LocalContext.current
    var text = remember(calculation = { "row 2" })
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "row 1")

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = text, Modifier.clickable {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            text = "text clicked"
        })
    }
}

@Composable
fun ColumnTest() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "column 1", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            Toast.makeText(context, "row text", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Button")
        }
    }
}
