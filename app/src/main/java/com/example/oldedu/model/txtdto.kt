package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class txtdto (
    @SerializedName("title") val title: String,
    @SerializedName("result") val result: List<txt>
        )