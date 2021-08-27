package com.slin.splayandroid.di

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import com.slin.splayandroid.utils.CookieUtils
import com.slin.splayandroid.utils.CookieUtils.readCookie
import com.slin.splayandroid.utils.CookieUtils.saveCookie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * author: slin
 * date: 2021/8/26
 * description:
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object CookieModule {

    private const val SAVE_USER_LOGIN_KEY = "user/login"
    private const val SAVE_USER_REGISTER_KEY = "user/register"
    private const val SET_COOKIE_KEY = "set-cookie"
    private const val COOKIE_NAME = "Cookie"

    /**
     * 登录时保存cookie
     */
    @Provides
    @Singleton
    @SaveCookieInterceptor
    fun provideSaveCookieInterceptor(dataStore: DataStore<Preferences>): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val response = chain.proceed(request)
                val requestUrl = request.url.toString()
                val domain = request.url.host
                // set-cookie maybe has multi, login to save cookie
                if ((requestUrl.contains(SAVE_USER_LOGIN_KEY) ||
                            requestUrl.contains(SAVE_USER_REGISTER_KEY))
                    && response.headers(SET_COOKIE_KEY).isNotEmpty()
                ) {
                    val cookies = response.headers(SET_COOKIE_KEY)
                    val cookie = CookieUtils.encodeCookie(cookies)
                    dataStore.saveCookie(requestUrl, domain, cookie)
                }
                return response
            }
        }
    }

    @Provides
    @Singleton
    @AddCookieInterceptor
    fun provideAddCookieInterceptor(dataStore: DataStore<Preferences>): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val builder = request.newBuilder()
                val domain = request.url.host
                if (domain.isNotEmpty()) {
                    val cookie = dataStore.readCookie(domain)
                    if (cookie.isNotEmpty()) {
                        builder.addHeader(COOKIE_NAME, cookie)
                    }
                }
                return chain.proceed(builder.build())
            }

        }
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SaveCookieInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AddCookieInterceptor