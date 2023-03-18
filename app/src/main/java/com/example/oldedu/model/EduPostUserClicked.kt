package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class EduPostUserClicked(
    @SerializedName("heartOnClicked")
    val heartOnClicked:Boolean,
    @SerializedName("scrapOnClicked")
    val scrapOnClicked:Boolean,
    @SerializedName("heartID")
    val heartID:String,
    @SerializedName("scrapID")
    val scrapID:String

)
