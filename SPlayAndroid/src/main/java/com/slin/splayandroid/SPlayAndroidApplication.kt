package com.slin.splayandroid

import android.app.Application
import com.slin.core.SCore
import com.slin.core.logger.initLogger
import dagger.hilt.android.HiltAndroidApp

/**
 * author: slin
 * date: 2021/6/8
 * description:
 *
 */
@HiltAndroidApp
class SPlayAndroidApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        SCore.init(this)
        SCore.initLogger(BuildConfig.DEBUG)
        SPlayAndroid.init(application = this)
    }


}