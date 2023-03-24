package com.example.oldedu.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class EduPhotoResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("result")
    val eduPhotoList: ArrayList<EduPhoto>

) {
    override fun toString(): String {
        return "EduPhotoResponse(eduPhotoList=$eduPhotoList)"
    }
}
