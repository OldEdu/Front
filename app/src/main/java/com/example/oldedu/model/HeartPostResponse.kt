package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class HeartPostResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("heartID") val heartID: String,
    @SerializedName("heart") val heart: Int,
        )