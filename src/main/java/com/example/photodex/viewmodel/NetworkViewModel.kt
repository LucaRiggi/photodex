package com.example.photodex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * View Model For Login
 */
class NetworkViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val _isNetworkAvailable = MutableStateFlow(true)
        val isNetworkAvailable = _isNetworkAvailable
    }


}