package com.example.oldedu.model

import com.google.gson.annotations.SerializedName

class CommentResponse (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val commentList: ArrayList<Comment>,
    @SerializedName("msg")
    val msg:String
    )
