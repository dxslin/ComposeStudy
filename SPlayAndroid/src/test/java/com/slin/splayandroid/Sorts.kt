package com.slin.splayandroid

import org.junit.Test

class Sorts {

    @Test
    fun sorts() {
        val arr = arrayOf(5, 1, 3, 2, 7, 6)

        for(i in 0..2){
            println(i)
        }

        quickSort(arr, 0, arr.size - 1)
        println(arr)
    }

    private fun quickSort(arr: Array<Int>, left: Int, right: Int) {
        var partitionIndex = 0
        while (left < right) {
            partitionIndex = partition(arr, left, right)
            quickSort(arr, left, partitionIndex - 1)
            quickSort(arr, partitionIndex + 1, right)
        }
    }

    private fun partition(arr: Array<Int>, left: Int, right: Int): Int {
        val pivot = left
        var index = left + 1
        for (i in index..right) {
            if (arr[pivot] > arr[i]) {
                swap(arr, i, index)
                index++
            }
        }
        swap(arr, pivot, index - 1)
        return index - 1
    }


    private fun swap(arr: Array<Int>, i: Int, j: Int) {
        val temp = arr[i]
        arr[i] = arr[j]
        arr[j] = temp
    }
}
