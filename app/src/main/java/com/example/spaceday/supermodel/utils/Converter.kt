package com.example.spaceday.supermodel.utils

import com.example.spaceday.supermodel.local.FavoriteEntity
import com.example.spaceday.supermodel.remote.NASAData
import com.google.gson.Gson
import org.w3c.dom.Entity

fun convertTypes(entityList: List<FavoriteEntity>) : List<NASAData>{
    val images : List<NASAData> = entityList.map {
        NASAData(it.date, it.explanation, it.hdurl, it.media_type, it.service_version, it.title, it.url)
    }
    return images
}

fun convertToEntity(image : NASAData) : FavoriteEntity {
    val entity = FavoriteEntity(
        0,
        image.date,
        image.explanation,
        image.hdurl,
        image.mediaType,
        image.service_version,
        image.title,
        image.url
    )
    return entity
}