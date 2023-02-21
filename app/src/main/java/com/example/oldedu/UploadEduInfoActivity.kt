package com.example.oldedu

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_upload_edu_info.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class CreatePost(
    @SerializedName("success")
    val success: Boolean ,
    @SerializedName("postID")
    val postID: String ,
)

data class PostData(
    @SerializedName("category")
    val category: String ,

    @SerializedName("title")
    val title: String ,

    @SerializedName("userID")
    val userID: String ,
)

interface CreatePostApi {
    @POST("createPost")
    fun postCreatePost(@Body postData: PostData):Call<CreatePost>
}

class UploadEduInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_edu_info)

        var cate:String = "transportation"
        val userID = intent.getStringExtra("userID").toString()
        var postID:String = ""

        var cateList = listOf("transportation", "search", "banking", "life style")
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cateList)
        spinner_cate.adapter = adapter
        spinner_cate.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                cate = cateList.get(p2)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        btn_submitPostInfo.setOnClickListener {
            val intent = Intent(this,EduImgListActivity::class.java)
            if (!edit_title.text.toString().isNullOrBlank()) {
                var postData = PostData(cate, edit_title.text.toString(),userID)
                postApi(postData)
                intent.putExtra("postTitle",edit_title.text.toString())
                intent.putExtra("postCate",cate)
                intent.putExtra("userID", userID)
                intent.putExtra("postID",postID)
                startActivity(intent)
            } else {
                val build = AlertDialog.Builder(this).setTitle("There is no title")
                    .setMessage("Please Write the title")
                build.show()
            }
        }


    }

    private fun postApi(data:PostData) {
        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(CreatePostApi::class.java)


        service.postCreatePost(data).enqueue(object : Callback<CreatePost>{
            override fun onResponse(call: Call<CreatePost>, response: Response<CreatePost>) {
                var result = response.body()?.postID
                Log.d("!!postApi result !!" , result.toString())
            }

            override fun onFailure(call: Call<CreatePost>, t: Throwable) {
                Log.d("postApi result","Failed")
            }

        })
    }

}