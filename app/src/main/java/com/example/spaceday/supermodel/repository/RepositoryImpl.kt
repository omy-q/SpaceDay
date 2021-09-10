package com.example.spaceday.supermodel.repository

import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.supermodel.remote.RemoteDataSource
import retrofit2.Callback

class RepositoryImpl :Repository {
    private val remoteDataSource = RemoteDataSource()
    override fun getServerData(callback: Callback<NASAData>) {
        remoteDataSource.loadDayImage(callback)
    }
}