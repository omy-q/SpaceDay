package com.example.spaceday.supermodel.utils

import com.example.spaceday.supermodel.local.FavoriteEntity
import com.example.spaceday.supermodel.remote.*

fun convertTypes(entityList: List<FavoriteEntity>) : List<NASAData>{
    val images : List<NASAData> = entityList.map {
        NASAData(it.date!!, it.explanation!!, it.hdurl!!, it.media_type!!, it.service_version!!, it.title!!, it.url!!)
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

fun convertEarthDTOtoData(earthDataDTO : ArrayList<EarthDataDTO>) : ArrayList<EarthData> {
    val earthData = arrayListOf<EarthData>()
    for (data in earthDataDTO){
        val separate = data.date.split("-", " ").map { it.trim() }
        val year = separate[0]
        val month = separate[1]
        val day = separate[2]
        val time = separate[3]
        val date = "$year-$month-$day"
        val url = "https://epic.gsfc.nasa.gov/archive/natural/" +
                "$year/$month/$day/png/${data.imageName}.png"
        earthData.add(EarthData(date, time, data.caption, url))
    }
    return earthData
}

fun convertNasaToMonthData(nasaData: ArrayList<NASAData>) : ArrayList<Pair<NASAData, Boolean>> {
    val pairData : ArrayList<Pair<NASAData, Boolean>> = arrayListOf()
    for (data in nasaData){
        pairData.add(Pair(data, false))
    }
    return pairData
}

fun convertMarsDTOtoPhoto (marsDataDTO: MarsDataDTO) : ArrayList<MarsPhoto> {
    val marsPhoto = arrayListOf<MarsPhoto>()
    for (data in marsDataDTO.photos){
        marsPhoto.add(MarsPhoto(data.image, data.camera.name, data.date))
    }
    return marsPhoto
}