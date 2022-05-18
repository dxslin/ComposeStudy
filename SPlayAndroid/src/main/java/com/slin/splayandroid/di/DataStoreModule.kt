package com.slin.splayandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * author: slin
 * date: 2021/8/26
 * description: 依赖注入项 DataSore
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    var ds: DataStore<Preferences>? = null

    @Provides
    @Singleton
    fun provideDefaultDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return preferencesDataStore("default_local_data.xml").getValue(context, DataStoreModule::ds)
    }


}