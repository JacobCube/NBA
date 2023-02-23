package com.example.nba.ext

import android.os.Build
import android.os.Bundle
import java.io.Serializable

/** Returns serializable from [Bundle] with pre-specified type */
inline fun <reified T: Serializable> Bundle.getSerializableCommon(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, clazz)
    } else {
        val x = getSerializable(key)
        return if(x is T) {
            getSerializable(key) as? T
        }else null
    }
}