package com.example.oldedu.educated

import com.example.oldedu.model.dto
import retrofit2.Call
import retrofit2.http.GET

interface edu_search {
    @GET("/heartPosts/search?output=json")
    fun getpost(

    ): Call<dto>
}