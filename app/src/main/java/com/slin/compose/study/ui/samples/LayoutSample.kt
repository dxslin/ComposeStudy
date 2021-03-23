package com.slin.compose.study.ui.samples

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.slin.compose.study.ui.theme.CsAppBar


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
        LayoutItem("2. ColumnTest") { ColumnTest() },
        LayoutItem("3. ConstraintTest") { ConstraintTest() },
    )

    Scaffold(scaffoldState = scaffoldState,
        topBar = { CsAppBar(isShowBack = true, title = "LayoutSample") }) {
        LazyColumn {
            items(layoutItems) { item ->
                TestItem(item = item)
            }
        }
    }
}


@Composable
fun TestItem(item: LayoutItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .background(color = Color.Gray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        item.content()
    }
}

@Preview
@Composable
fun RowTest() {
    val context = LocalContext.current
    val (text, setValue) = remember(calculation = { mutableStateOf("row 2") })
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
            setValue("text clicked")
        })
    }
}

@Preview
@Composable
fun ColumnTest() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
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

@Preview
@Composable
fun ConstraintTest() {
    val context = LocalContext.current
    ConstraintLayout {
        val (text, button) = createRefs()
        Text(text = "text",
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(8.dp))

        Button(
            onClick = { Toast.makeText(context, "Button Click", Toast.LENGTH_SHORT).show() },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(text.bottom)
                start.linkTo(text.start)
            }
        ) {
            Text(text = "Button")
        }

    }

}