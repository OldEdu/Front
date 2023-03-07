package com.example.oldedu.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class edu (
    @SerializedName("title") val title: String,
    @SerializedName ("userName") val userName: String,
    @SerializedName("category") val category: String,
    @SerializedName("heart") val heart: Int,
    @SerializedName("views") val views: Int,
    @SerializedName ("postID")  val postID: String,
    @SerializedName("comment") val comment:Int


    ): Parcelable