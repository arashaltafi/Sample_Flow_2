package com.arash.altafi.sampleflow2.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogResponseMoshi(
    @Json(name = "message")
    val message: String,
    @Json(name = "status")
    val status: String
)