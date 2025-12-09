package com.example.photodex.data.picture

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictures")
data class Picture (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageLink: String,
)