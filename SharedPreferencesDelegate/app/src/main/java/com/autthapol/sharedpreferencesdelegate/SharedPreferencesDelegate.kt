package com.autthapol.sharedpreferencesdelegate

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesDelegate<T>(
    private val context: Context,
    private val key: String,
    private val defaultValue: T?
) : ReadWriteProperty<Any?, T?> {

    private val sharedPreferences by lazy {
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return when (defaultValue) {
            is String -> {
                (sharedPreferences.getString(key, "") ?: defaultValue)
            }
            else -> null // Add more cases for other types
        } as T?
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        when (value) {
            is String -> {
                sharedPreferences.edit().putString(key, value).apply()
            }
        }
    }
}

fun <T> Context.sharedPreferences(name: String, defaultValue: T?) =
    SharedPreferencesDelegate(this, name, defaultValue)