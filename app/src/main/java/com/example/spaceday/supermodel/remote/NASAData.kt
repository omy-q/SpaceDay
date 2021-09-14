package com.example.spaceday.supermodel.remote

import com.google.gson.annotations.SerializedName

data class NASAData (
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("hdurl") val hdurl: String?,
    @field:SerializedName("media_type") val mediaType: String?,
    @field:SerializedName("service_version") val service_version: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("url") val url: String?,
)