package com.example.oldedu.educated

import com.example.oldedu.model.dto
import retrofit2.Call
import retrofit2.http.GET

interface edu_economic3 {
    @GET("/recentPosts/economic?output=json")
    fun getpost(

    ): Call<dto>
}