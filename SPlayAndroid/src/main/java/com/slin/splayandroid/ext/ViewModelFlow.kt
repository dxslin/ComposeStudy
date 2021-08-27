package com.slin.splayandroid.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlin.experimental.ExperimentalTypeInference

/**
 * author: slin
 * date: 2021/8/27
 * description:
 *
 */

const val STOP_TIMEOUT_MILLIS = 5000L


/**
 * 在[ViewModel]中创建[StateFlow]，
 *
 * 使用示例：
 * ```
 * val bannerFlow: StateFlow<List<BannerBean>> = stateFlow(listOf()){
 *     emit(homeRepository.getBanner())
 * }
 *```
 * 在View层观察数据时：
 * ```
 * lifecycleScope.launch {
 *     lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
 *         homeViewModel.bannerFlow.collect {
 *             log { "banner: ${it.joinToString()}" }
 *         }
 *     }
 * }
 * ```
 *
 * [started]接受三个值：
 * - [SharingStarted.Lazily] 当首个订阅者出现时开始，当scope(这里默认为viewModelScope)指定作用域结束时终止
 * - [SharingStarted.Eagerly] 立即开始，当scope(这里默认为viewModelScope)指定作用域结束时终止
 * - [SharingStarted.WhileSubscribed] 该策略会在没有收集器的情况下延迟stopTimeoutMillis毫秒之后取消上游数据流，
 *      这样可以保证旋转销毁界面或者用户来回切换界面时，数据不会丢失，但是如果用户离开界面久了，不会再占用资源保存数据，
 *      也能保证下次进来时不会使用过期的数据。
 *
 * @param initialValue 初始值，或者数据重置时返回的默认值
 * @param started   上游数据开始停止策略，详情见[SharingStarted]
 * @param block     创建Flow的数据流块
 */
@OptIn(ExperimentalTypeInference::class)
fun <T> ViewModel.stateFlow(
    initialValue: T,
    started: SharingStarted = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
    @BuilderInference block: suspend FlowCollector<T>.() -> Unit
): StateFlow<T> {
    return flow<T>(block)
        .stateIn(
            scope = viewModelScope,
            started = started,
            initialValue = initialValue
        )
}