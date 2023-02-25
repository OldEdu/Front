package com.example.oldedu.educated

import com.example.oldedu.model.dto
import com.example.oldedu.model.searchdto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface edu_search {
    @GET("/heartPosts/search?output=json")
    fun getpost(

    ): Call<dto>

    @GET("/searchHeartPosts/search?output=json")
    fun getpost1(
        @Query("keyword") keyword:String

    ): Call<searchdto>
}