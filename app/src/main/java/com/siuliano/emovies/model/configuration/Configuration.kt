package com.siuliano.emovies.model.configuration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//TODO create converter to avoid this class
@JsonClass(generateAdapter = true)
data class ConfigurationDTO(
    val images: ImagesConfigurationDTO
)

@JsonClass(generateAdapter = true)
data class ImagesConfigurationDTO(
    @Json(name = "secure_base_url")
    val secureBaseUrl: String,
    val poster_sizes: List<String>,
)

object Configuration {
    var secureBaseUrl: String = ""
    var smallSize: String = ""
    var largeSize: String = ""
}