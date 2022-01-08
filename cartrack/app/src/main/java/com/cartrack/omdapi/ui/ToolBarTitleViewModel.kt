package com.cartrack.omdapi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import com.cartrack.omdapi.R
import com.cartrack.omdapi.utils.StringUtils.getString

class ToolBarTitleViewModel : ViewModel() {

    val toolbarTitle = MutableLiveData<String>()

    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }

    fun setDestination(destination: NavDestination) {
        var title = ""
        when (destination.id) {
            R.id.mediaListFragment -> {
                title = getString(R.string.title_media_list_screen)
            }
        }
        setToolbarTitle(title)
    }

}