package com.example.spaceday.superview.viewmodel

import com.example.spaceday.supermodel.remote.*

sealed interface AppState{
    data class Success(val serverResponseData: NASAData) : AppState
    data class SuccessEarthData(val serverResponseData: ArrayList<EarthData>) : AppState
    data class SuccessMarsData(val serverResponseData: ArrayList<MarsPhoto>) : AppState
    data class SuccessPlanetaryData(val serverResponseData: PlanetaryData) : AppState
    data class SuccessDetails(val dbResponseData: List<NASAData>) : AppState
    data class SuccessMonthData(val serverResponseData: ArrayList<Pair<NASAData, Boolean>>) : AppState
    data class Error(val error: Throwable) : AppState
    object Loading : AppState
}