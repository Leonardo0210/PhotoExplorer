package com.example.photoexplorer.data

import com.example.photoexplorer.model.Photo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {

    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): List<Photo>

    companion object {
        private const val BASE_URL = "https://picsum.photos/"

        val service: PhotoApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PhotoApi::class.java)
        }
    }
}

