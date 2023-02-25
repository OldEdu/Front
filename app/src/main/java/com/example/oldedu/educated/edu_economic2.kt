package com.example.oldedu.educated

import com.example.oldedu.model.dto
import com.example.oldedu.model.searchdto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface edu_economic2 {
    @GET("/viewsPosts/economic?output=json")
    fun getpost(

    ): Call<dto>

    @GET("/searchViewsPosts/economic?output=json")
    fun getpost1(
        @Query("keyword") keyword:String

    ): Call<searchdto>
}