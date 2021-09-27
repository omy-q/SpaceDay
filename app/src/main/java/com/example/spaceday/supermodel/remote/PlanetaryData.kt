package com.example.spaceday.supermodel.remote

import com.google.gson.annotations.SerializedName

data class PlanetaryData(
    @field:SerializedName("date") val date : String,
    @field:SerializedName("url") val image : String
)
