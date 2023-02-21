package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class edu (
    @SerializedName("title") val title: String,
    @SerializedName("userID") val userID: String,
    @SerializedName("category") val category: String
        )