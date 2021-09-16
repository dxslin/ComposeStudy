package com.slin.splayandroid

import com.slin.splayandroid.di.DaggerSPlayAndroidComponent
import com.slin.splayandroid.di.SPlayAndroidComponent
import com.slin.splayandroid.di.SPlayAndroidComponentDependencies
import dagger.hilt.android.EntryPointAccessors

/**
 * author: slin
 * date: 2021/9/16
 * description: 与应用Application相关的常量
 *
 */
object SPlayAndroid {

    lateinit var application: SPlayAndroidApplication

    lateinit var sPlayAndroidComponent: SPlayAndroidComponent

    fun init(application: SPlayAndroidApplication) {
        this.application = application
        this.sPlayAndroidComponent = DaggerSPlayAndroidComponent.builder()
            .sPlayAndroidDependencies(
                EntryPointAccessors.fromApplication(
                    application,
                    SPlayAndroidComponentDependencies::class.java
                )
            )
            .context(application)
            .build()

    }

}