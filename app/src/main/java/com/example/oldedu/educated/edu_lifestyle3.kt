package com.example.oldedu.educated

import com.example.oldedu.model.dto
import retrofit2.Call
import retrofit2.http.GET

interface edu_lifestyle3 {
    @GET("/recentPosts/life style?output=json")
    fun getpost(

    ): Call<dto>
}