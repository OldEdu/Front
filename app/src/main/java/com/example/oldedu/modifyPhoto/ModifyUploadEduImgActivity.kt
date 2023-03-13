package com.example.oldedu.modifyPhoto
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.Keep
import com.bumptech.glide.Glide
import com.example.oldedu.R
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_modify_upload_edu_img.*
import kotlinx.android.synthetic.main.activity_mypage_student.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

@Keep
data class GetEduImgData(
    @SerializedName("eduPhotoID")
    val eduPhotoID: String ,

    @SerializedName("postID")
    val postID: String ,

    @SerializedName("imgNum")
    val imgNum: Number ,

    @SerializedName("imgUrl")
    val imgUrl: String ,

    @SerializedName("voiceGuide")
    val voiceGuide: String,

    @SerializedName("textGuide")
    val textGuide: String,
)

@Keep
data class GetEduPhotoResult(
    @SerializedName("success")
    val success: Boolean ,

    @SerializedName("eduPhotoResponse")
    val eduPhotoResponse: GetEduImgData ,
)

interface GetPhotoApi {
    @GET("getEduPhoto/{eduPhotoID}")
    fun  getEduPhoto(@Path("eduPhotoID") eduPhotoID: String): Call<GetEduPhotoResult>
}

class ModifyUploadEduImgActivity : AppCompatActivity() {
    private val OPEN_GALLERY = 1
    var currentImgUrl:Uri? = null
    var imgUrl:String = ""
    var textGuide :String = ""
    var voiceGuide :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_edu_img)

        val eduPhotoID = intent.getStringExtra("eduPhotoID")

        if (eduPhotoID != null) {
            val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(
                GsonConverterFactory.create()).build()
            val service = retrofit.create(GetPhotoApi::class.java)

            service.getEduPhoto(eduPhotoID).enqueue(object : Callback<GetEduPhotoResult> {
                override fun onResponse(call: Call<GetEduPhotoResult>, response: Response<GetEduPhotoResult>) {
                    val response = response.body()?.eduPhotoResponse
                    if (response != null) {
                        imgUrl = response.imgUrl
                        textGuide = response.textGuide
                        voiceGuide = response.voiceGuide
                        Glide.with(this@ModifyUploadEduImgActivity)
                            .load(imgUrl)
                            .fallback(R.drawable.logo)
                            .into(imgView_screenImg)
                    }
                }

                override fun onFailure(call: Call<GetEduPhotoResult>, t: Throwable) {
                    Log.d("error" , t.message.toString())
                }

            })
        }

        btn_changeImg.setOnClickListener {
            openGallery()
            imgUrl = currentImgUrl.toString()
        }
        btn_deleteImg.setOnClickListener { handleDelImgBtn() }
        btn_modifyPost.setOnClickListener {
            if(!currentImgUrl.toString().isNullOrBlank()) {
                Log.d("test~~~~~~", textGuide)
                val intent = Intent(this,ModifyUploadEduTextActivity::class.java)
                intent.putExtra("imgUrl",currentImgUrl.toString())
                intent.putExtra("eduPhotoID" , eduPhotoID)
                intent.putExtra("textGuide", textGuide)
                intent.putExtra("voiceGuide", voiceGuide)
                startActivity(intent)
            } else {
                val build = AlertDialog.Builder(this).setTitle("There is no image")
                    .setMessage("Please upload the image")
                build.show()
            }
        }
    }

    private fun openGallery() {
        val intent:Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,OPEN_GALLERY)
    }

    private fun handleDelImgBtn() {
        currentImgUrl = null
        imgView_screenImg.setImageBitmap(null)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {
                currentImgUrl = data?.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,currentImgUrl)
                    imgView_screenImg.setImageBitmap(bitmap)
                    Log.d("currentImgUri !! : ",currentImgUrl.toString())

                }catch (e:Exception) {
                    e.printStackTrace()
                }
            }
        } else{
            Log.d("ActivityResult", "Wrong!")
        }
    }

}
