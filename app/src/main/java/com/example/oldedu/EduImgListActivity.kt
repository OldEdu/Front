package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oldedu.adapter.EduImgListRecyclerAdapter
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

    @SerializedName("imgNum")
    val imgNum: Number,
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
//    private lateinit var mAdapter: EduImgListRecyclerAdapter
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

        btn_registerPost.setOnClickListener{
            val intent = Intent(this,MypageTeacherActivity::class.java)
            intent.putExtra("userID",userID)
            startActivity(intent)
        }

    }

    private fun setAdapter(photoList : List<ImgItemData>){
        val mAdapter = EduImgListRecyclerAdapter(photoList,this)
        recView_eduImgList.adapter = mAdapter
        recView_eduImgList.layoutManager = LinearLayoutManager(this)
    }

    private fun getEduPhotoApi(postID: String) {
        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(ImgListApi::class.java)

        service.getEduPhoto(postID).enqueue(object : Callback<ImgListResultData>{
            override fun onResponse(call: Call<ImgListResultData>, response: Response<ImgListResultData>
            ) {
                if(response.isSuccessful.not()) { return }
                if(!response.body()?.result.isNullOrEmpty()) {
                    response.body()?.let {

                        Log.d("%% 이미지리스트 결과 데이터" , it.result.toString())
                        setAdapter(it.result)

                    }
                }
            }

            override fun onFailure(call: Call<ImgListResultData>, t: Throwable) {
                Log.d("API MSG",t.message.toString())
            }

        })
}}