package com.slin.splayandroid.di

import com.google.gson.Gson
import com.slin.splayandroid.ui.test.TestRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * author: slin
 * date: 2021/9/16
 * description:
 *
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface SPlayAndroidComponentDependencies {

    fun gson(): Gson

    fun testRepository(): TestRepository

}