package com.example.spaceday.supermodel.repository

import com.example.spaceday.supermodel.local.repository.CallbackDB
import com.example.spaceday.supermodel.remote.*
import retrofit2.Callback

interface Repository {
    fun getServerData(callback: Callback<NASAData>)
    fun getDateServerData(date : String, callback: Callback<NASAData>)
    fun getMonthServerData(startDate : String, endDate : String, callback: Callback<ArrayList<NASAData>>)
    fun getEarthServerData(callback: Callback<ArrayList<EarthDataDTO>>)
    fun getMarsServerData(callback: Callback<MarsDataDTO>)
    fun getMarsServerDataByDate(date: String, callback: Callback<MarsDataDTO>)
    fun getPlanetaryServerData(lat: String, lon: String, callback: Callback<PlanetaryData>)
    fun getFavoriteImages(callback : CallbackDB)
    fun deleteFavoriteImage(image : NASAData)
    fun saveFavoriteImage(image : NASAData)
}