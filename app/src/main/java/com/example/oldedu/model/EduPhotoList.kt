package com.example.oldedu.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class EduPhotoList(
        @SerializedName("imgUrl")
        val imgUrl: String?,
        @SerializedName("eduPhotoID")
        val eduPhotoID: String?,
        @SerializedName("textGuide")
        val textGuide: String?,
        @SerializedName("imgNum")
        val imgNum: Int,
        @SerializedName("postID")
        val postID: String?,
        @SerializedName("voiceGuide")
        val voiceGuide: String?

        )


