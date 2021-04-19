package com.slin.compose.study

import android.app.Application
import com.slin.core.logger.initLogger

class ComposeStudyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger(BuildConfig.DEBUG)
    }

}