package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

class MycontentsResponse (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val contentsList: ArrayList<Mycontents>
)