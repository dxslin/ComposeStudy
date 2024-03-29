package com.slin.splayandroid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.slin.core.logger.logd
import com.slin.core.ui.CoreActivity
import com.slin.splayandroid.nav.NavGraphs
import com.slin.splayandroid.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * register compose view
 *
 */

@AndroidEntryPoint
class MainActivity : CoreActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        logd { "strings: ${getString(R.string.param1, "11")}" }

        setContent {

            AppTheme {
                ProvideWindowInsets {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        NavGraphs()
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        Greeting("Android")
    }
}