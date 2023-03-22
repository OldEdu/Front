package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("comtID")
    val comtID:String,
    @SerializedName("comt_content")
    var comt_content:String,
    @SerializedName("comt_date")
    val comt_date:String,
    @SerializedName("postID")
    val userID:String,
    @SerializedName("userName")
    val userName:String,
    @SerializedName("term")
    val term:String,
    @SerializedName("myComment")
    val myComment:Boolean
    )

