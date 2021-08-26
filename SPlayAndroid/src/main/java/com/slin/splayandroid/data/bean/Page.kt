package com.slin.splayandroid.data.bean

data class Page<T>(
    val curPage: Int,
    val offset: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: T
)