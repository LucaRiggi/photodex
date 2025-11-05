package com.example.photodex.data.user

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.photodex.data.user.User
import kotlinx.coroutines.flow.Flow

interface UserDAO {
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(users: User)

    @Delete
    suspend fun delete(users: User)

    @Update
    suspend fun update(users: User)
}