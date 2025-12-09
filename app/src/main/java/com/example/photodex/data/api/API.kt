package com.example.photodex.data.api

import com.example.photodex.data.api.models.Results
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoAPI {
    @GET("photos")
    suspend fun getPhotos(): List<Results>

    @GET("search")
    suspend fun getResults(@Query("query") query: String): SearchResponse
}

data class SearchResponse(
    val results: List<Results>
)

/*
 *
 * !!!!!!!!!!!! CLANKER ALERT !!!!!!!!!!!!!!
 *
 * Gemini 3 (Thinking & Reasoning) was used to fix the API Key not working
 * on api call. (I suck, confirmed.)
 *
 */

object RetrofitClient {
    private const val PROXY_API_KEY = "askj39f0234jkfw023rjksd"
    private const val BASE_URL = "http://167.86.126.238:2096/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("x-api-key", PROXY_API_KEY)
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }
        .build()

    val api: PhotoAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PhotoAPI::class.java)
}