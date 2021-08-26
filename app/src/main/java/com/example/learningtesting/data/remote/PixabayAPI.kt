package com.example.learningtesting.data.remote

import com.example.learningtesting.BuildConfig
import com.example.learningtesting.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    fun getImagefromPixabay(
        @Query("q") searchQuery:String,
        @Query("key") apiKey : String = BuildConfig.API_KEY
    ): Response<ImageResponse>

}