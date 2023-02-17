package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_mypage_teacher.*
import kotlinx.android.synthetic.main.activity_mypage_teacher.btn_back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


class educated1Activity : AppCompatActivity() {
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

        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(educated1Api::class.java)

        val getIntent = getIntent()
        val heart = getIntent.getIntExtra("heart",0)

        service.getheart(heart).enqueue(object : Callback<educated1> {
            override fun onResponse(call: Call<educated1>, response: Response<educated1>) {
                var result = response.body()
                Log.d("테스트","test"+response)


            }

            override fun onFailure(call: Call<educated1>, t: Throwable) {
                Log.d("retrofit","실패")
            }


        })


    }
}

@Keep
data class educated1(
    @SerializedName("heart")
    val views: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("in_date")
    val in_date: String,

    @SerializedName("heart")
    val heart: Int,

    @SerializedName("declaratation")
    val declaration: Int,

    @SerializedName("category")
    val category: String,

    @SerializedName("postId")
    val postId: String
)

interface educated1Api {
    @GET("heartpost/life style")
    fun getheart(@Path("category") heart: Int): Call<educated1>
}

