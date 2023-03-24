package com.swein.framework.utility.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// top-level define
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "com.swein.shjetpackcompose")

object DataStoreManager {

    suspend fun saveValue(context: Context, key: String, value: String) {

        val wrappedKey = stringPreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun saveValue(context: Context, key: String, value: Int) {
        val wrappedKey = intPreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun saveValue(context: Context, key: String, value: Double) {

        val wrappedKey = doublePreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun saveValue(context: Context, key: String, value: Long) {

        val wrappedKey = longPreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun saveValue(context: Context, key: String, value: Boolean) {

        val wrappedKey = booleanPreferencesKey(key)
        context.settingsDataStore.edit {
            it[wrappedKey] = value
        }
    }

    suspend fun getStringValue(context: Context, key: String, default: String = ""): String {

        val wrappedKey = stringPreferencesKey(key)
        val valueFlow: Flow<String> = context.settingsDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }

    suspend fun getIntValue(context: Context, key: String, default: Int = 0): Int {

        val wrappedKey = intPreferencesKey(key)
        val valueFlow: Flow<Int> = context.settingsDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }

    suspend fun getDoubleValue(context: Context, key: String, default: Double = 0.0): Double {

        val wrappedKey = doublePreferencesKey(key)
        val valueFlow: Flow<Double> = context.settingsDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }

    suspend fun getLongValue(context: Context, key: String, default: Long = 0L): Long {

        val wrappedKey = longPreferencesKey(key)
        val valueFlow: Flow<Long> = context.settingsDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }

    suspend fun getBooleanValue(context: Context, key: String, default: Boolean = false): Boolean {

        val wrappedKey = booleanPreferencesKey(key)
        val valueFlow: Flow<Boolean> = context.settingsDataStore.data.map {
            it[wrappedKey] ?: default
        }
        return valueFlow.first()
    }
}