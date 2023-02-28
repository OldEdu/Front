package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

data class voicedto (
    @SerializedName("voiceGuide") val voiceGuide: String,
    @SerializedName("result") val result: List<txt>
)