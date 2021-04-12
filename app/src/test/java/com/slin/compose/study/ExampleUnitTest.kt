package com.slin.compose.study

import androidx.compose.ui.layout.Placeable
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val centerSize = 3

        val placeables = IntArray(6) {
            it + 1
        }.toList()

        println(placeables)

        val centerOffset = (centerSize + 1) * centerSize / 2;
        check(placeables.size >= centerOffset) { "centerSize太大，而子布局数量不足" }

        var lineCount = 1
        var preTotalCount = lineCount
        var height = 0
        var lineHeight = 0

        var index = 0
        var line = 1

        placeables.groupBy(keySelector = {placeable ->
            val key = line
            if ((index++ + 1) == preTotalCount) {
                height += lineHeight
                lineHeight = 0

                if (preTotalCount < (centerSize + 1) * centerSize / 2 ) {
                    lineCount++
                } else {
                    lineCount--
                }
                preTotalCount += lineCount
                line++
            }
            key
        }).forEach { t, u ->
            println("key: $t, value: $u")
        }
    }
}
