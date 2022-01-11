package com.cartrack.omdapi.utils

import androidx.annotation.StringRes
import com.cartrack.omdapi.MainApplication

object StringUtils {
    fun getString(@StringRes resId: Int) : String {
        return MainApplication.appContext.getString(resId)
    }
}