package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class txtdto (
    @SerializedName("textGuide") val textGuide: String,
    @SerializedName("result") val result: List<txt>
        )