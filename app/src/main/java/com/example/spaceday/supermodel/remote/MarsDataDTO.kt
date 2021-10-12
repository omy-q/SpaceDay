package com.example.spaceday.supermodel.remote

import com.google.gson.annotations.SerializedName

data class MarsDataDTO(
    @field:SerializedName("photos") var photos : ArrayList<MarsPhotoDTO>
)

data class MarsPhotoDTO(
    @field:SerializedName("img_src") val image : String,
    @field:SerializedName("earth_date") val date : String,
    @field:SerializedName("camera") val camera : CameraName
)

data class CameraName(
    @field:SerializedName("full_name") val name : String
)