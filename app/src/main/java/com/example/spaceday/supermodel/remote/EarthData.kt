package com.example.spaceday.supermodel.remote

import com.google.gson.annotations.SerializedName

data class EarthData(
    @field:SerializedName("date") val date : String,
    @field:SerializedName("image") val imageName : String,
    @field:SerializedName("caption") val caption : String
)
