package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class ScrapPostResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("scrapID") val scrapID: String,
    @SerializedName("scrap") val scrap: Int
)
