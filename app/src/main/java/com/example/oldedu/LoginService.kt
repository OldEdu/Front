package com.example.oldedu

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("/login/")
    fun requestLogin(
        @Field("userID") userID:String,
        @Field("psword") psword:String
    ) : Call<Login>
}