package com.example.oldedu.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class txt (
    @SerializedName("title") val title: String,
    @SerializedName ("postID")  val postID: String,
    @SerializedName ("textGuide")  val textGuide: String,
    @SerializedName ("voiceGuide")  val voiceGuide: String



): Parcelable