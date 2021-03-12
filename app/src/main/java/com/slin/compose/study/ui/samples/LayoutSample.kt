package com.slin.compose.study.ui.samples

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
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
@Preview
@Composable
fun LayoutSample() {

    Scaffold(topBar = { com.slin.compose.study.ui.theme.AppBar() }) {
        LazyColumn(modifier = Modifier.padding(Padding.medium)) {
            item { TestItem("RowTest") { RowTest() } }
        }
    }

}


@Composable
fun TestItem(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = MaterialTheme.typography.h6)
        content()
    }
}

@Composable
fun RowTest() {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "row test")

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            Toast.makeText(context, "row text", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Button")
        }
    }
}
