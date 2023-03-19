package com.example.oldedu

import kotlinx.android.synthetic.main.activity_mypage_student.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
//import android.telecom.Call
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_mypage_student.back_btn
import kotlinx.android.synthetic.main.activity_mypage_student.btn_home
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//data class MyPageRequestDTO (
//    @SerializedName("userID") var userID: String
//        )

@Keep
data class MyPageStudent(
    @SerializedName("userID")
    val userID: String ,

    @SerializedName("psword")
    val psword: String ,

    @SerializedName("userName")
    val userName: String ,

    @SerializedName("userType")
    val userType: Boolean


)

interface MyPageStudentApi {
    @GET("profile/{userID}")
    fun  getUserID(@Path("userID") userID: String): Call<MyPageStudent>
}

class MypageStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_student)

        btn_home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        back_btn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(MyPageStudentApi::class.java)

        val userID = intent.getStringExtra("userID").toString()



        service.getUserID(userID).enqueue(object : Callback<MyPageStudent> {
            override fun onResponse(
                call: Call<MyPageStudent>,
                response: Response<MyPageStudent>
            ) {
//                    var result : MyPageStudent? = response.body()
                var result = response.body()
                Log.d("테스트","test"+response)
                textView_name.text = result?.userName
                Log.d("retrofit","성공"+result?.userID)
                Log.d("retrofit","성공"+result?.userName)
            }

            override fun onFailure(call: Call<MyPageStudent>, t: Throwable) {
                Log.d("retrofit","실패")
            }

        })

        btn_scrap.setOnClickListener({
            val intent = Intent(this, MyscrapActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        })


    }
}