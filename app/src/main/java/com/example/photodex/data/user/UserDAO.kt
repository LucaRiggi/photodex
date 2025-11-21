package com.example.photodex.data.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsers(): Flow<List<User>>

    // custom queries
    // https://developer.android.com/training/data-storage/room/accessing-data#kotlin

    // to check if user is already registered
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?


    // to check if the user is there (please admire cryptography)
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): User?
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(users: User)

    @Delete
    suspend fun delete(users: User)

    @Update
    suspend fun update(users: User)
}