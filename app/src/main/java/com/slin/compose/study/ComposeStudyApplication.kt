package com.slin.compose.study

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.slin.compose.study.weight.UnsplashSizingInterceptor
import com.slin.core.logger.initLogger

@Suppress("unused")
class ComposeStudyApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        initLogger(BuildConfig.DEBUG)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .componentRegistry {
                add(UnsplashSizingInterceptor)
            }.build()
    }

}