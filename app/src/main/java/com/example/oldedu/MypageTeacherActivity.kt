package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import com.example.oldedu.LoginActivity.Companion.userID
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
data class MyPageTeacher(
    @SerializedName("userID")
    val userID: String ,

    @SerializedName("psword")
    val psword: String ,

    @SerializedName("userName")
    val userName: String ,

    @SerializedName("userType")
    val userType: Boolean,

    @SerializedName("profileDesc")
    val profileDesc: String,

    @SerializedName("profilePhoto")
    val profilePhoto: String,

    @SerializedName("heart")
    val heart: Int
)

interface MyPageTeacherApi {
    @GET("profile/{userID}")
    fun  getUserID(@Path("userID") userID: String): Call<MyPageTeacher>
}

class MypageTeacherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_teacher)

        home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_myContents.setOnClickListener{
            val intent = Intent(this,mycontents::class.java)
            intent.putExtra("userID",userID)
            startActivity(intent)
        }


        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(MyPageTeacherApi::class.java)

//        val getIntent = getIntent()
//        val userID = getIntent.getStringExtra("userID").toString()
        val userID = intent.getStringExtra("userID").toString()
        Log.d("여기는 선생님 마이페이지" , userID)

        service.getUserID(userID).enqueue(object : Callback<MyPageTeacher> {
            override fun onResponse(call: Call<MyPageTeacher>, response: Response<MyPageTeacher>) {
                var result = response.body()
                Log.d("테스트","test"+response)
                textView_name.text = result?.userName
                textView_like.text = "like : " + result?.heart

                Log.d("retrofit","성공"+result?.userID)
                Log.d("retrofit","성공"+result?.userName)
            }

            override fun onFailure(call: Call<MyPageTeacher>, t: Throwable) {
                Log.d("retrofit","실패")
            }


        })

        // Make a new Content Button
        btn_makeNewContent.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("userID",userID)
            startActivity(intent)
        }
    }
}