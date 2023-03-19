package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class Scrap (
    @SerializedName("userID") val userID:String,
    @SerializedName("title") val title: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("category") val category: String,
    @SerializedName("heart") val heart: Int,
    @SerializedName("views") val views: Int,
    @SerializedName("postID")  val postID: String,
    @SerializedName("comment") val comment:Int,
    @SerializedName("scrap") val scrap:Int,
    @SerializedName("in_date") val in_date:String
        )