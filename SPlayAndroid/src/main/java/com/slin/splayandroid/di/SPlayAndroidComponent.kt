package com.slin.splayandroid.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component

/**
 * author: slin
 * date: 2021/9/16
 * description:
 *
 */

@Component(dependencies = [SPlayAndroidComponentDependencies::class])
interface SPlayAndroidComponent {

    fun inject(application: Application)

    fun gson(): Gson

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder

        fun sPlayAndroidDependencies(dependencies: SPlayAndroidComponentDependencies): Builder

        fun build(): SPlayAndroidComponent
    }

}