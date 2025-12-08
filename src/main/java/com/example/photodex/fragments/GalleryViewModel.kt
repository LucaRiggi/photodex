package com.example.photodex.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photodex.data.api.RetrofitClient
import com.example.photodex.data.api.models.Results
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
 *
 * !!!!!!!!!!!!! GEMINI ALERT !!!!!!!!!!!!
 *
 * Gemini 3 (Thinking & Reasoning) was used to assist on fixing issues in this file
 * it was also used to recode my horrible messy obnoxious absolutely down right horrendous
 *
 * giant function blocks....... (The were like 10-12 lines each... don't ask... its to complicated to explain..)
 *
 */

class GalleryViewModel() : ViewModel() {

    private val _images = MutableStateFlow<List<Results>>(emptyList())
    val images = _images.asStateFlow()

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            try {
                _images.value = RetrofitClient.api.getPhotos()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchImages(query: String) {
        viewModelScope.launch {
            try {
                if (query.isBlank()) {
                    fetchImages()
                } else {
                    val response = RetrofitClient.api.getResults(query)
                    _images.value = response.results
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}