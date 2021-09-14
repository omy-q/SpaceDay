package com.example.spaceday.supermodel.local.repository

import com.example.spaceday.supermodel.remote.NASAData

interface CallbackDB {
    fun onResponse(result : List<NASAData>)
}