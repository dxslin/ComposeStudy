package com.slin.compose.buildSrc

object Versions {


    const val androidSdk = 30
    const val androidBuildTools = "30.0.3"
    const val androidMinSdk = 24

    const val ktlint = "0.40.0"

}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.0-alpha08"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object Slin {
        private const val snapshot = "score_snapshot"
        private const val version = "1.1.0"
        const val score = "io.github.dxslin:Score:$version"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:1.3.0"
    }

    /**
     * Google Compose 扩展库
     * doc:https://google.github.io/accompanist/
     */
    object Accompanist {
        private const val version = "0.16.0"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val theme = "com.google.accompanist:accompanist-appcompat-theme:$version"
        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val flowlayout = "com.google.accompanist:accompanist-flowlayout:$version"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
    }

    object Kotlin {
        private const val version = "1.5.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.5.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object JUnit {
        private const val version = "4.13"
        const val junit = "junit:junit:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val material = "com.google.android.material:material:1.4.0"
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"

        object Compose {
            const val snapshot = ""
            const val version = "1.0.1"

            const val animation = "androidx.compose.animation:animation:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val material = "androidx.compose.material:material:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"

            const val livedata = "androidx.compose.runtime:runtime-livedata:$version"


        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.1"
        }

        object Jetpack {
            private const val version = "2.3.1"
            private const val hiltVersion = "2.38.1"
            private const val hiltViewModelVersion = "1.0.0-alpha02"

            const val livedata = "androidx.lifecycle:lifecycle-livedata:${version}"
            const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${version}"

            const val hilt = "com.google.dagger:hilt-android:${hiltVersion}"
            const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${hiltVersion}"
            const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${hiltVersion}"
            const val hiltViewModel =
                "androidx.hilt:hilt-lifecycle-viewmodel:${hiltViewModelVersion}"
            const val hiltViewModelCompiler = "androidx.hilt:hilt-compiler:${hiltViewModelVersion}"


        }

        object Navigation {
            private const val version = "2.3.5"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"

            const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha06"
        }

        object ConstraintLayout {
            const val constraintLayoutCompose =
                "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"
        }

        object Test {
            private const val version = "1.3.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        }
    }


}
