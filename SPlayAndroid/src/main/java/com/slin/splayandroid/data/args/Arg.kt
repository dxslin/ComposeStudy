package com.slin.splayandroid.data.args

import com.slin.splayandroid.constants.PagingConst

/**
 * author: slin
 * date: 2021/8/31
 * description:
 *
 */


/**
 * 起始页码
 */
private const val START_PAGE_NUM = PagingConst.START_PAGE_NUM

sealed class Arg<A : Arg<A>>(val page: Int) {

    abstract fun copy(page: Int): A

    fun previous(): A? {
        return if (page > START_PAGE_NUM) copy(page - 1) else null
    }

    fun next(): A {
        return copy(page + 1)
    }

    fun reset(): A {
        return copy(START_PAGE_NUM)
    }
}

class Arg0(page: Int = START_PAGE_NUM) : Arg<Arg0>(page) {
    override fun copy(page: Int): Arg0 {
        return Arg0(page)
    }

}

class Arg1<T>(page: Int = START_PAGE_NUM, val arg1: T?) : Arg<Arg1<T>>(page) {
    override fun copy(page: Int): Arg1<T> {
        return Arg1(page, arg1)
    }
}

class Arg2<T, R>(page: Int = START_PAGE_NUM, val arg1: T?, val arg2: R?) : Arg<Arg2<T, R>>(page) {
    override fun copy(page: Int): Arg2<T, R> {
        return Arg2(page, arg1, arg2)
    }
}

