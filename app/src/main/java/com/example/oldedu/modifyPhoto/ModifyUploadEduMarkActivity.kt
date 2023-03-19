package com.example.oldedu.modifyPhoto
import com.example.oldedu.EduImgListActivity
import com.example.oldedu.R
import android.Manifest.*
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_modify_upload_edu_mark.*
import kotlinx.android.synthetic.main.activity_modify_upload_edu_mark.btn_next
import kotlinx.android.synthetic.main.activity_modify_upload_edu_mark.imgView_screenImg
import kotlinx.android.synthetic.main.activity_upload_edu_mark.layout_imgMark
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

@Keep
data class CreateEduImgResult(
    @SerializedName("success")
    val success: Boolean ,
)

@Keep
data class CreateEduImgData(
    @SerializedName("eduPhotoID")
    val eduPhotoID: String ,

    @SerializedName("imgUrl")
    val imgUrl: String ,

    @SerializedName("voiceGuide")
    val voiceGuide: String,

    @SerializedName("textGuide")
    val textGuide: String,
)

interface CreateEduImgApi {
    @POST("updateEduPhoto")
    fun  postCreateEduPhoto(@Body createEduImgData: CreateEduImgData): Call<CreateEduImgResult>
}

class ModifyUploadEduMarkActivity : AppCompatActivity() {

    var startX=200.0f
    var startY=200.0f
    var leftMargin = 0
    var topMargin = 0
    var imgWidth=0.0f
    var imgHeight=0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_upload_edu_mark)

        mark_box.bringToFront()
        mark_box1.bringToFront()
        mark_box2.bringToFront()

        val postID = intent.getStringExtra("postID").toString()
        val eduPhotoID = intent.getStringExtra("eduPhotoID").toString()
        val imgUrl = intent.getStringExtra("imgUrl")
        val voiceGuide = intent.getStringExtra("voiceGuide").toString()
        val textGuide = intent.getStringExtra("textGuide").toString()
        var currBox:Int = 0
        val currentImgUrl = intent.getStringExtra("imgUrl").toString()
//        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(currentImgUrl))
//        imgView_screenImg.setImageBitmap(bitmap)
        Glide.with(this)
            .load(imgUrl)
            .fallback(R.drawable.logo)
            .into(imgView_screenImg)

        val boxes:List<ImageView> = listOf(mark_box,mark_box1,mark_box2)

        btn_createBox.setOnClickListener {
            if (currBox>2) {
                return@setOnClickListener
            }
            if (boxes[2].visibility == View.VISIBLE) {
                Toast.makeText(this,"Only three marks can be created",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!mark_X.text.toString().isNullOrBlank() and !mark_Y.text.toString().isNullOrBlank()) {
                imgWidth = mark_X.text.toString().toFloat()
                imgHeight = mark_Y.text.toString().toFloat()
                handleMark(boxes[currBox])
                mark_X.text = null
                mark_Y.text = null
                if (currBox < 2) {
                    currBox += 1
                }
            } else {
                Toast.makeText(this,"Please set the size ",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

        }

        for (i in boxes) {
            i.setOnTouchListener { v, e ->
                when(e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = e.x
                        startY = e.y
                    }
                    ACTION_MOVE ->{
                        var diffX=e.x-startX
                        var diffY=e.y-startY

                        leftMargin += diffX.toInt()
                        topMargin += diffY.toInt()

                        val layoutParams= FrameLayout.LayoutParams(
                            imgWidth.toInt(),
                            imgHeight.toInt()
                        )
                        layoutParams.leftMargin=leftMargin
                        layoutParams.topMargin=topMargin

                        i.layoutParams=layoutParams
                    }
                    MotionEvent.ACTION_UP->{
                    }

                }
                return@setOnTouchListener true
            }
        }

        btn_return.setOnClickListener {
            if (currBox>=0) {
                boxes[currBox].visibility = View.GONE
                if (currBox>0) {
                    currBox -= 1
                }
            }
            return@setOnClickListener
        }


        btn_next.setOnClickListener {
            val intent = Intent(this, EduImgListActivity::class.java)
            intent.putExtra("postID", postID)
            intent.putExtra("img", "")

            val CAPTURE_PATH = ""
            ActivityCompat.requestPermissions(this, arrayOf(permission.WRITE_EXTERNAL_STORAGE), 1)
            layout_imgMark?.buildDrawingCache()
            val captureView: Bitmap? = layout_imgMark?.drawToBitmap()
            val fos: FileOutputStream

            val strFolderPath = getExternalFilesDir(null)?.absolutePath + CAPTURE_PATH
            val folder = File(strFolderPath)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val strFilePath = strFolderPath + "/" + System.currentTimeMillis() + ".png"
            val fileCacheItem = File(strFilePath)

            try {
                fos = FileOutputStream(fileCacheItem)
                captureView?.compress(Bitmap.CompressFormat.PNG, 100, fos)
                val stream = FileInputStream(File(strFilePath))

                Log.d("%%%%stream" , stream.toString())

                var imgTime = System.currentTimeMillis()
                var uploadTask = Firebase.storage.reference
                    .child("/images/${postID}_${imgTime}.png")
                    .putStream(stream)

                Log.d("###imgTime" , imgTime.toString())
                Log.d("###uploadtask" , uploadTask.toString())
                uploadTask.addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener {
                        Log.d("uri?", it.path.toString())
                        Log.d("uri????", it.toString())

                        var createEduImgData = CreateEduImgData(eduPhotoID,it.toString(),voiceGuide,textGuide)
                        postApi(createEduImgData)

                        intent.putExtra("markedImg", strFilePath)
                        startActivity(intent)
                    }

                }
                    .addOnFailureListener { e ->
                        Log.d("nopenopenope" , e.message.toString())
                    }
                Log.d("***uploadTask" , uploadTask.toString())

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } finally {
                Toast.makeText(this, "COMPLETE", Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun handleMark(box: ImageView) {
        box.visibility =  View.VISIBLE
    }

    private fun postApi(createEduImgData:CreateEduImgData) {
        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(CreateEduImgApi::class.java)

        service.postCreateEduPhoto(createEduImgData).enqueue(object : Callback<CreateEduImgResult> {
            override fun onResponse(call: Call<CreateEduImgResult>, response: Response<CreateEduImgResult>
            ) {
                var result = response.body()?.success
                if (result != null) {
                    Log.d("성공..?",result.toString())
                }
            }

            override fun onFailure(call: Call<CreateEduImgResult>, t: Throwable) {
                Log.d("failed postApi result",t.message.toString())
            }


        })
    }

}