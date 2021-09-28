package com.example.spaceday.supermodel.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceDayAPI {
    @GET("planetary/apod")
    fun getImageOfTheDay(
        @Query("api_key") apiKey: String
    ): Call<NASAData>

    @GET("planetary/apod")
    fun getImageOfTheDate(
        @Query("api_key") apiKey: String,
        @Query("date") date :String
    ): Call<NASAData>

    @GET("EPIC/api/natural")
    fun getEarthImage(
        @Query("api_key") apiKey: String,
    ): Call<ArrayList<EarthDataDTO>>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImage(
        @Query("api_key") apiKey: String,
        @Query("earth_date") earthDate : String
    ): Call<MarsData>

    @GET("planetary/earth/assets")
    fun getPlanetaryImage(
        @Query("api_key") apiKey: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Call<PlanetaryData>
}