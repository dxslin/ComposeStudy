package androidx.lifecycle

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalLifecycleOwner

/**
 * author: slin
 * date: 2021/6/2
 * description:
 *
 */

/**
 * 效果同[observeAsState]，但是[observeAsState]在[LiveData]设置相同的对象之后不会更新[Composable]界面；
 * 比如，更新列表里面的数据之后，在[LiveData]里面再次设置改列表对象，可以[Observer]中能监听到设置，
 * 但是[observeAsState]不会更新，而[observeAsStateAny]总是会更新
 *
 * @sample com.slin.compose.study.ui.samples.UserModel
 */
@Composable
fun <T> LiveData<T>.observeAsStateAny(): State<T?> = observeAsStateAny(value)

/**
 * Starts observing this [LiveData] and represents its values via [State]. Every time there would
 * be new value posted into the [LiveData] the returned [State] will be updated causing
 * recomposition of every [State.value] usage.
 *
 * The inner observer will automatically be removed when this composable disposes or the current
 * [LifecycleOwner] moves to the [Lifecycle.State.DESTROYED] state.
 *
 * @sample androidx.compose.runtime.livedata.samples.LiveDataWithInitialSample
 */
@Composable
fun <R, T : R> LiveData<T>.observeAsStateAny(initial: R): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    var stateVersion by remember { mutableStateOf(0) }
    val state = remember(stateVersion) { mutableStateOf(initial) }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> {
            stateVersion++
            state.value = it
        }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }
    return state
}
