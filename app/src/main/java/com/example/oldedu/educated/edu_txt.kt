package com.example.oldedu.educated

import com.example.oldedu.model.txtdto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface edu_txt {
    @GET("/eduPhoto/1tSrMCXH6ZvtThzoRu12?output=json")
    fun getpost(

    ): Call<txtdto>
}