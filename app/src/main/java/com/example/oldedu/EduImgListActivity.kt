package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_edu_img_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

@Keep
data class ImgItemData(
    @SerializedName("postID")
    val postID: String ,

    @SerializedName("eduPhotoID")
    val eduPhotoID: String ,

    @SerializedName("imgUrl")
    val imgUrl: String ,

    @SerializedName("voiceGuide")
    val voiceGuide: String,

    @SerializedName("textGuide")
    val textGuide: String,
)

data class ImgListResultData (
    @SerializedName("success")
    val success: Boolean ,

    @SerializedName("result")
    val result: List<ImgItemData> ,
    )

interface ImgListApi {
    @GET("eduPhoto/{postID}")
    fun  getEduPhoto(@Path("postID") postID: String): Call<ImgListResultData>
}

class EduImgListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edu_img_list)

        var postID = intent.getStringExtra("postID")
        var userID = intent.getStringExtra("userID")
        var postCate = intent.getStringExtra("postCate")
        var postTitle = intent.getStringExtra("postTitle")
        if(intent.hasExtra("markedImg")) {
            var markedImg = intent.getStringExtra("markedImg")
        }

        if (postID != null) {
            getEduPhotoApi(postID)
            Log.d("EduImgListActivity" , postID)
        }

        btn_createImg.setOnClickListener {
            val intent = Intent(this,UploadEduImgActivity::class.java)
            intent.putExtra("postID",postID)
            startActivity(intent)
        }

    }

    private fun getEduPhotoApi(postID: String) {
        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(ImgListApi::class.java)

        service.getEduPhoto(postID).enqueue(object : Callback<ImgListResultData>{
            override fun onResponse(call: Call<ImgListResultData>, response: Response<ImgListResultData>
            ) {
                if(response.isSuccessful.not()) { return }
                response.body()?.let {
                    if (it.result!=null) {
                        it.result.forEach { (x) -> Log.d("TEST!!!",x.toString()) }
                    } else {return@let}

                }
            }

            override fun onFailure(call: Call<ImgListResultData>, t: Throwable) {
                Log.d("API MSG","IMGLISTRESULTDATA API FAILED !")
            }

        })
}}