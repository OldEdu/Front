package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class ScrapResponse (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val contentsList: ArrayList<Scrap>
        )