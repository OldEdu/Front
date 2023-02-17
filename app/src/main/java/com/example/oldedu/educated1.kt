package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_mypage_teacher.*
import kotlinx.android.synthetic.main.activity_mypage_teacher.btn_back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query


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




    }
}

object RetrofitClass {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://34.168.110.14:8080/heartPosts")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _api = retrofit.create(heartInterface::class.java)


}

data class heartresult(
    val result: List<result>
    )

data class result(
    @SerializedName("heart")
    val title: String,
    val userId: String,
    val in_date: String,
    val views: Int,
    val heart: Int,
    val declaration: Int,
    val category: String,
    val postId: String

)

interface heartInterface{

    fun getResult(@Query("title") title: String,
                  @Query("in_date") in_date:String,
                  @Query("category") category: String,
                  @Query("postId") postId: String

    ): Call<heartresult>
}

