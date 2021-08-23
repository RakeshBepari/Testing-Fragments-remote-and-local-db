package com.example.learningtesting.data.remote.responses

import com.example.learningtesting.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.learningtesting.data.remote.responses.ImageResponse

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query ("key") apiKey : String = BuildConfig.API_KEY
    ) : Response<ImageResponse>

}