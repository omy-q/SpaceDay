package com.example.spaceday.supermodel.repository

import com.example.spaceday.supermodel.local.repository.CallbackDB
import com.example.spaceday.supermodel.remote.NASAData
import retrofit2.Callback

interface Repository {
    fun getServerData(callback: Callback<NASAData>)
    fun getDateServerData(date : String, callback: Callback<NASAData>)
    fun getFavoriteImages(callback : CallbackDB)
    fun deleteFavoriteImage(image : NASAData)
    fun saveFavoriteImage(image : NASAData)
}