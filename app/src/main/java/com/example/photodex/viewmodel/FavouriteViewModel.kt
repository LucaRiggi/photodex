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

    // TODO: REMOVE ONCE LUCA IS DONE WITH API
    fun generateDummyPics() {
        viewModelScope.launch(Dispatchers.IO) {
            val dummyData = listOf(
                Picture(
                    name = "Test1",
                    imageLink = "https://cdn.pixabay.com/photo/2014/03/10/18/44/boar-284685_960_720.jpg"
                ),
                Picture(
                    name = "Test2",
                    imageLink = "https://cdn.pixabay.com/photo/2018/10/06/19/46/boar-3728550_960_720.jpg"
                ),
                Picture(
                    name = "Test3",
                    imageLink = "https://cdn.pixabay.com/photo/2018/05/09/22/44/piglet-3386356_1280.jpg"
                ),
                Picture(
                    name = "Test4",
                    imageLink = "https://cdn.pixabay.com/photo/2022/02/23/22/13/pig-7031521_960_720.jpg"
                )
            )

            dummyData.forEach {
                repository.insertPicture(it)
            }
        }
    }


}