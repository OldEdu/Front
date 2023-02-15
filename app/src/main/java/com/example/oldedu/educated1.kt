package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add.*
import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mypage_student.*
import kotlinx.android.synthetic.main.activity_mypage_teacher.*
import kotlinx.android.synthetic.main.activity_mypage_teacher.btn_back
import kotlinx.android.synthetic.main.activity_mypage_teacher.textView_name
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

@Keep
data class educated(
    @SerializedName("heart")
    val heart: String ,




)

interface educatedapi {
    @GET("heartPosts/{heart}")
    fun  getUserID(@Path("heart") heart: String): Call<educated>
}

class educated1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educated1)

        home.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        btn_back.setOnClickListener({
            val intent = Intent(this, CatActivity::class.java)
            startActivity(intent)
        })

        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/searchViewsPosts?keyword=카카오").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(educatedapi::class.java)

        val getIntent = getIntent()
        val userID = getIntent.getStringExtra("heart").toString()



    }
}