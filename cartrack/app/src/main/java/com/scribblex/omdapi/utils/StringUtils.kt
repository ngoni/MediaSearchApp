package com.scribblex.omdapi.utils

import androidx.annotation.StringRes
import com.scribblex.omdapi.MainApplication

object StringUtils {
    fun getString(@StringRes resId: Int) : String {
        return MainApplication.appContext.getString(resId)
    }
}