package com.slin.compose.study

import org.junit.Test

/**
 * author: slin
 * date: 2021/5/7
 * description:
 *
 */
class ExampleMethodTest {

    @Test
    fun methodTest() {

        val tt: (Int) -> Unit = { i -> println("haha $i") }

        tt(1)


        val func23: (
            Int, Int, Int, Int, Int, Int, Int, Int, Int,
            Int, Int, Int, Int, Int, Int, Int, Int, Int,
            Int, Int, Int, Int, Int, Int, Int, Int, Int
        ) -> Unit =
            { i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25, i26, i27 ->
                println("slin: $i1, $i2")
            }

        func23(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7)

    }


}