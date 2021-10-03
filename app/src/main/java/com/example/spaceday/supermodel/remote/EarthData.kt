package com.example.spaceday.supermodel.remote

import com.google.gson.annotations.SerializedName

data class EarthData(
    val date : String,
    val time : String,
    val caption : String,
    var url : String
)