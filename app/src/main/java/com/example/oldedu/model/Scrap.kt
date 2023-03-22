package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class Scrap (
    @SerializedName("userID")
    val userID:String,

    @SerializedName("title")
    val title:String,

    @SerializedName("postID")
    val postID:String


)