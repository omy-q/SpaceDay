package com.example.spaceday.supermodel.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceDayAPI {
    @GET("planetary/apod")
    fun getImageOfTheDay(
        @Query("api_key") apiKey: String)
            : Call<NASAData>

    @GET("planetary/apod")
    fun getImageOfTheDate(
        @Query("api_key") apiKey: String,
        @Query("date") date :String
    ) : Call<NASAData>
}