package com.slin.compose.study.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)


@Composable
fun ComposeStudyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    paddings: Paddings = ComposeStudyTheme.paddings,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    AppTheme(colors = colors, paddings = paddings, content = content)
}

/**
 * 主题  <p>
 * 在这里设置`MaterialTheme`以及一些与主题相关的`Composition Local`变量
 *
 */
@Composable
fun AppTheme(
    colors: Colors,
    paddings: Paddings = ComposeStudyTheme.paddings,
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(LocalPaddings provides paddings.copy()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

/**
 * 保存与主题相关的一些Local变量
 */
object ComposeStudyTheme {

    val paddings: Paddings
        @Composable
        get() = LocalPaddings.current

    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography


    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

}



private val PinkDarkColorPalette = darkColors(
    primary = Pink200,
    primaryVariant = Pink700,
    secondary = Purple200
)

private val PinkLightColorPalette = lightColors(
    primary = Pink500,
    primaryVariant = Pink700,
    secondary = Purple200
)

@Composable
fun PinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    paddings: Paddings = ComposeStudyTheme.paddings,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        PinkDarkColorPalette
    } else {
        PinkLightColorPalette
    }
    AppTheme(colors = colors, paddings = paddings, content = content)
}


private val BlueDarkColorPalette = darkColors(
    primary = Blue200,
    primaryVariant = Blue700,
    secondary = Pink200
)

private val BlueLightColorPalette = lightColors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Pink200
)

@Composable
fun BlueTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    paddings: Paddings = ComposeStudyTheme.paddings,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        BlueDarkColorPalette
    } else {
        BlueLightColorPalette
    }
    AppTheme(colors = colors, paddings = paddings, content = content)
}