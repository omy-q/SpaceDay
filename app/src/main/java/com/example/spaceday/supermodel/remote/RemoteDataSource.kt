package com.example.spaceday.supermodel.remote

import com.example.spaceday.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val BASE_URL = "https://api.nasa.gov/"

    private val apiSource = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory
            .create(GsonBuilder().setLenient().create()))
        .build()
        .create(SpaceDayAPI::class.java)

    fun loadTodayImage(callback : Callback<NASAData>){
        apiSource.getImageOfTheDay(BuildConfig.NASA_API_KEY).enqueue(callback)
    }

    fun loadDateImage(date : String, callback: Callback<NASAData>){
        apiSource.getImageOfTheDate(BuildConfig.NASA_API_KEY, date).enqueue(callback)
    }

    fun loadEarthImage(callback : Callback<ArrayList<EarthDataDTO>>){
        apiSource.getEarthImage(BuildConfig.NASA_API_KEY).enqueue(callback)
    }

    fun loadMarsImageByDate(date : String, callback: Callback<MarsData>){
        apiSource.getMarsImage(BuildConfig.NASA_API_KEY, date).enqueue(callback)
    }

    fun loadPlanetaryImageByLatLon(lat : String, lon : String, callback: Callback<PlanetaryData>){
        apiSource.getPlanetaryImage(BuildConfig.NASA_API_KEY, lat, lon).enqueue(callback)
    }
}