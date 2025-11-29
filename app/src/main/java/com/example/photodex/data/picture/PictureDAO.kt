package com.example.photodex.data.picture

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDAO {
    @Query("SELECT * FROM pictures ORDER BY id DESC")
    fun getAllPictures(): Flow<List<Picture>>

    @Query("SELECT * FROM pictures WHERE id = :id")
    fun getPictureById(id: Int): Flow<Picture>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(pictures: Picture)

    @Delete
    suspend fun delete(pictures: Picture)

    @Update
    suspend fun update(pictures: Picture)
}