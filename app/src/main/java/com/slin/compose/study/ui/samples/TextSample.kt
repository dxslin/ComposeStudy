package com.slin.compose.study.ui.samples

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slin.compose.study.ui.theme.ComposeStudyTheme
import com.slin.compose.study.ui.theme.ScaffoldWithCsAppBar
import com.slin.compose.study.ui.theme.Size
import com.slin.core.logger.logd
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

/**
 * author: slin
 * date: 2021/4/21
 * description:
 *
 */

@Preview
@Composable
fun TextSample() {
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val textItems = listOf(
        LayoutItem("1. TextFont") { TextFont() },
        LayoutItem("2. TextAnnotatedString") { TextAnnotatedString() },
        LayoutItem("3. SelectableText") { SelectableText() },
        LayoutItem("4. SimpleClickableText") { SimpleClickableText() },
        LayoutItem("5. SimpleTextField") { SimpleTextField(name, password) },
    )

    ScaffoldWithCsAppBar(title = "TextSample") { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = Size.medium)
        ) {
            textItems.forEach { textItem ->
                item { TestItem(item = textItem) }
            }
        }
    }
}

@Preview
@Composable
private fun TextFont() {
    Column {
        Text(text = "Hello World")
        Text(
            text = "Hello World, color = ComposeStudyTheme.colors.primary",
            color = ComposeStudyTheme.colors.primary
        )
        Text(text = "Hello World, fontSize = 18.sp", fontSize = 18.sp)
        Text(
            text = "Hello World, fontWeight = FontWeight.ExtraBold",
            fontWeight = FontWeight.ExtraBold
        )
        Text(text = "Hello World, fontStyle = FontStyle.Italic", fontStyle = FontStyle.Italic)
        Text(text = "Hello World, fontFamily = FontFamily.Cursive", fontFamily = FontFamily.Cursive)
        Text(text = "Hello World, letterSpacing = 6.sp", letterSpacing = 6.sp)
        Text(
            text = "Hello World, textDecoration = TextDecoration.Underline",
            textDecoration = TextDecoration.Underline
        )
        Text(
            text = "Hello World, textDecoration = TextDecoration.LineThrough",
            textDecoration = TextDecoration.LineThrough
        )
        Text(
            text = "Hello World, textAlign = TextAlign.End",
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Hello World, lineHeight = 24.sp", lineHeight = 24.sp)
        Text(
            text = "Hello World, overflow = TextOverflow.Ellipsis, maxLines = 1",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun TextAnnotatedString() {
    Column {
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = ComposeStudyTheme.colors.primary,
                    fontSynthesis = FontSynthesis.Style,
                )
            ) {
                append("H")
            }
            append("ello ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("W")
            }
            append("orld")
            //ParagraphStyle段落风格总是会换行
            withStyle(
                style = ParagraphStyle(
                    lineHeight = 24.sp,
                    textIndent = TextIndent(firstLine = 40.sp, restLine = 20.sp)    //缩进
                )
            ) {
                append("Hello \n World")
            }
        })
    }
}

@Preview
@Composable
private fun SelectableText() {
    //可选区域
    SelectionContainer {
        Row {
            Text(text = "Hello World, ")
            //禁止选中区域
            DisableSelection {
                Text(text = "Copy Prohibited")
            }
        }
    }
}

@Preview
@Composable
fun SimpleClickableText() {
    val context = LocalContext.current
    Column {
        ClickableText(
            text = AnnotatedString(
                text = "Click Me",
                paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center)
            ),
            modifier = Modifier
                .height(24.dp)
        ) { offset ->
            logd { "ClickableText, $offset -th character is clicked." }
            Toast.makeText(
                context,
                "ClickableText, $offset -th character is clicked.",
                Toast.LENGTH_SHORT
            ).show()
        }

        val annotatedText = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textAlign = TextAlign.Center)) {
                append("Click ")
                //push一个URL注解，直到pop方法，中间均为点击区域
                pushStringAnnotation(tag = "URL", annotation = "http://www.baidu.com")
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("here")
                }
                pop()
            }
        }
        ClickableText(
            text = annotatedText,
            modifier = Modifier
                .height(24.dp)
                .wrapContentHeight(Alignment.CenterVertically)
        ) { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()
                ?.let { annotation ->
                    logd { "Clicked URL, ${annotation.item}" }
                    Toast.makeText(
                        context,
                        "Clicked URL, ${annotation.item}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}

/**
 * 1. TextField中keyboardOptions参数可以设置键盘模式和键盘行为（需要设置singleLine=true），keyboardActions中处理键盘行为事件
 * 2. FocusRequester可以用来处理焦点切换和请求焦点
 */
@Composable
fun SimpleTextField(name: MutableState<String>, password: MutableState<String>) {
    val passwordFocus = remember { FocusRequester() }

    Column {
        TextField(
            value = name.value,
            onValueChange = { t ->
                name.value = t
                logd { "name = ${name.value}" }
            },
            label = { Text("Email") },
            modifier = Modifier.padding(bottom = Size.medium),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                passwordFocus.requestFocus()
            }),
            maxLines = 1,
            singleLine = true,
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { t ->
                password.value = t
                logd { "password = ${password.value}" }
            },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation('*'),
            modifier = Modifier.focusRequester(passwordFocus),
            maxLines = 1,
            singleLine = true,
        )
    }
}

@Preview
@Composable
fun PreviewSimpleTextField() {
    SimpleTextField(name = remember { mutableStateOf("") },
        password = remember { mutableStateOf("") })
}