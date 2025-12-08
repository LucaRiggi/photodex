package com.example.photodex.data.api.models

data class Results(
    val id: String,
    val alt_description: String?,
    val urls: Urls
)

data class Urls(
    val regular: String,
    val small: String
)