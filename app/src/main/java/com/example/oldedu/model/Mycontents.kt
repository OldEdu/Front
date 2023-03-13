package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class Mycontents (
    @SerializedName("userID")
    val userID:String,

    @SerializedName("title")
    val title:String,

    @SerializedName("postID")
    val postID:String


        )