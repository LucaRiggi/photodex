package com.example.photodex.viewmodel

import PictureRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.photodex.data.database.AppDatabase
import com.example.photodex.data.picture.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel for favourite fragment
 */
class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PictureRepository
    val allPictures: LiveData<List<Picture>>


    init {
        val dao = AppDatabase.getDatabase(context = application).pictureDao()
        repository = PictureRepository(dao)
        allPictures = repository.getAllPictures().asLiveData()
    }

    fun getPicture(id: Int): Flow<Picture> {
        return repository.getPictureById(id)
    }

    fun favourite(picture: Picture) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPicture(picture)
        }
    }

    fun unfavourite(picture: Picture) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePicture(picture)
        }
    }

}