package com.slin.splayandroid.ext

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * author: slin
 * date: 2021/8/26
 * description:
 *
 */

fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
    return data.map { preferences ->
        preferences[key] ?: defaultValue
    }
}

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    edit { preferences ->
        preferences[key] = value
    }
}

suspend fun DataStore<Preferences>.clear() {
    edit { it.clear() }
}
