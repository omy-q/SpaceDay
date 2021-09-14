package com.example.spaceday.superview.viewmodel

import com.example.spaceday.supermodel.remote.NASAData

sealed interface AppState{
    data class Success(val serverResponseData: NASAData) : AppState
    data class SuccessDetails(val dbResponseData: List<NASAData>) : AppState
    data class Error(val error: Throwable) : AppState
    object Loading : AppState
}