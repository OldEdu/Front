package com.example.oldedu.educated

import com.example.oldedu.model.dto
import com.example.oldedu.model.searchdto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface edu_transport {
    @GET("/searchRecentPosts/transportation?output=json")
    fun getpost(
        @Query("keyword") keyword:String

    ): Call<searchdto>

}