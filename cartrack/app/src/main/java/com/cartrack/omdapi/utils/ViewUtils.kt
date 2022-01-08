package com.cartrack.omdapi.utils

import androidx.core.widget.ContentLoadingProgressBar

object ViewUtils {
    fun hideProgressBar(progressBar: ContentLoadingProgressBar) {
        progressBar.hide()
    }

    fun showProgressBar(progressBar: ContentLoadingProgressBar) {
        progressBar.show()
    }
}