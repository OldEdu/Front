package com.example.oldedu.educated

import com.example.oldedu.model.dto
import retrofit2.Call
import retrofit2.http.GET

interface edu_transport3 {
    @GET("/recentPosts/transportation?output=json")
    fun getpost(

    ): Call<dto>

}