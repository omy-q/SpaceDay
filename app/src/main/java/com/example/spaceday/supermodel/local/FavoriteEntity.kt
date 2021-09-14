package com.example.spaceday.supermodel.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title : String?,
    val url : String?
)