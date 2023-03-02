package com.example.oldedu.educated

import com.example.oldedu.model.txtdto
import com.example.oldedu.model.voicedto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface edu_txt {
    @GET("/eduPhoto/{postID}?output=json")
    fun getpost(


    ): Call<txtdto>

    @GET("/eduPhoto/{postiID}?output=json")
    fun getpost1(

    ): Call<voicedto>

}