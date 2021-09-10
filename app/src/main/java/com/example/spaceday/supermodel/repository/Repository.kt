package com.example.spaceday.supermodel.repository

import com.example.spaceday.supermodel.remote.NASAData
import retrofit2.Callback

interface Repository {
    fun getServerData(callback: Callback<NASAData>)
}