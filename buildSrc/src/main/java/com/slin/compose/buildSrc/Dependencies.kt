package com.slin.compose.buildSrc

import org.gradle.api.JavaVersion

object Versions {


    const val androidSdk = 33
    const val androidBuildTools = "30.0.3"
    const val androidMinSdk = 24

    val javaVersion = JavaVersion.VERSION_17

    const val ktlint = "0.40.0"

}

object Libs {
    private const val agpVersion = "8.0.2"
    const val androidGradlePlugin = "com.android.tools.build:gradle:$agpVersion"


    object Slin {
        private const val snapshot = "score_snapshot"
        private const val version = "1.1.5"
        const val score = "io.github.dxslin:Score:$version"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:1.3.0"
    }

    /**
     * Google Compose 扩展库
     * doc: https://google.github.io/accompanist/
     */
    object Accompanist {
        private const val version = "0.28.0"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val theme = "com.google.accompanist:accompanist-appcompat-theme:$version"
        const val systemUiController =
            "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val flowlayout = "com.google.accompanist:accompanist-flowlayout:$version"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$version"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$version"
        const val drawablePainter = "com.google.accompanist:accompanist-drawablepainter:$version"
        const val permissions = "com.google.accompanist:accompanist-permissions:$version"
        const val navMaterial = "com.google.accompanist:accompanist-navigation-material:$version"
        const val navAnimation = "com.google.accompanist:accompanist-navigation-animation:$version"
    }

    object Kotlin {
        private const val version = "1.8.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"

        object Coroutines {
            private const val version = "1.6.4"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object AndroidX {
        /**
         * https://github.com/material-components/material-components-android/releases
         */
        const val material = "com.google.android.material:material:1.5.0"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/appcompat
         */
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/compose
         */
        object Compose {
            const val snapshot = ""
            /**
             * https://androidx.dev/storage/compose-compiler/repository
             */
            const val composeVersion = "1.4.3"
            const val version = "1.4.3"

            const val animation = "androidx.compose.animation:animation"
            const val foundation = "androidx.compose.foundation:foundation"
            const val layout = "androidx.compose.foundation:foundation-layout"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val material = "androidx.compose.material:material"
            const val runtime = "androidx.compose.runtime:runtime"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val ui = "androidx.compose.ui:ui"
            const val uiUtil = "androidx.compose.ui:ui-util"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:"

            const val livedata = "androidx.compose.runtime:runtime-livedata:$version"


        }

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/activity
         */
        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }

        /**
         * https://developer.android.google.cn/jetpack/androidx/explorer
         */
        object Jetpack {
            /**
             * https://developer.android.google.cn/jetpack/androidx/releases/lifecycle
             */
            private const val lifecycleVersion = "2.6.1"

            /**
             * https://dagger.dev/hilt/gradle-setup
             * https://developer.android.google.cn/jetpack/androidx/releases/hilt
             */
            private const val hiltVersion = "2.46.1"
            private const val hiltComposeVersion = "1.0.0"

            /**
             * https://developer.android.google.cn/jetpack/androidx/releases/room
             */
            private const val roomVersion = "2.5.0"

            /**
             * https://developer.android.google.cn/jetpack/androidx/releases/paging
             */
            private const val pagingComposeVersion = "1.0.0-alpha18"
            private const val pagingVersion = "3.1.1"


            const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val lifecycleViewModel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

            const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
            const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
            const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
            const val hiltNavigationCompose =
                "androidx.hilt:hilt-navigation-compose:$hiltComposeVersion"
            const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:$hiltVersion"

            const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
            const val roomKtx = "androidx.room:room-ktx:$roomVersion"
            const val roomCompiler = "androidx.room:room-compiler:$roomVersion"

            const val paging = "androidx.paging:paging-common:$pagingVersion"
            const val pagingCompose = "androidx.paging:paging-compose:$pagingComposeVersion"

        }

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/navigation
         */
        object Navigation {
            private const val version = "2.5.3"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"

            const val compose = "androidx.navigation:navigation-compose:$version"
        }

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/constraintlayout
         */
        object ConstraintLayout {
            const val constraintLayoutCompose =
                "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"
        }

    }

    object Logger {
        /**
         * https://github.com/JakeWharton/timber
         */
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

    object Test {
        private const val junit_version = "4.13"
        private const val version = "1.3.0"

        const val junit = "junit:junit:$junit_version"
        const val core = "androidx.test:core:$version"
        const val rules = "androidx.test:rules:$version"

        object Ext {
            private const val version = "1.1.2"
            const val junit = "androidx.test.ext:junit-ktx:$version"
        }

        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }

}
