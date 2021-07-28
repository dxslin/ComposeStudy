package com.slin.compose.study

import org.junit.Test
import kotlin.random.Random

/**
 * author: slin
 * date: 2021/5/7
 * description:
 *
 */
class ExampleMethodTest {

    @Test
    fun methodTest() {
        repeat(10) {
            println("slin: ${Random.nextInt(0, 10)}")
        }

    }

    @Test
    fun finallyReturnTest() {
        println("test: ${finallyReturn()}")
    }

    class Person(var name: String) {
        override fun toString(): String {
            return "Person(name='$name')"
        }
    }

    fun finallyReturn(): Person? {

        var a: Person = Person("a")
        try {
            a = Person("b")
            return a
        } catch (e: Exception) {

        } finally {
            a.name = "c"
            println("finally $a")
        }
        return null
    }


}