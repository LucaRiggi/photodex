package com.example.photodex.data.api

import com.example.photodex.data.api.models.Results
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoAPI {
    @GET("photos")
    suspend fun getPhotos(): List<Results>

    @GET("search")
    fun getResults(@Query("query") results: String): Call<Results>
}

object RetrofitClient {
    val api: PhotoAPI = Retrofit.Builder()
        .baseUrl("http://167.86.126.238:2096/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PhotoAPI::class.java)
}