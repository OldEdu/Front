package com.example.oldedu

import android.Manifest.*
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.example.oldedu.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_upload_edu_mark.*
import kotlinx.android.synthetic.main.activity_upload_edu_mark.layout_imgMark
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

@Keep
data class CreateEduImgResult(
    @SerializedName("success")
    val success: Boolean ,
)

//interface CreateEduImgApi {
//    @Multipart
//    @POST("createEduPhoto")
//    fun  postCreateEduPhoto(
//        @Part ("postID") postID : String,
//        @Part imgUrl : MultipartBody.Part,
//        @Part ("voiceGuide") voiceGuide : String,
//        @Part ("textGuide") textGuide : String
//    ): Call<CreateEduImgResult>
//}

@Keep
data class CreateEduImgData(
    @SerializedName("postID")
    val postID: String ,

    @SerializedName("imgUrl")
    val imgUrl: String ,

    @SerializedName("voiceGuide")
    val voiceGuide: String,

    @SerializedName("textGuide")
    val textGuide: String,
)

interface CreateEduImgApi {
    @POST("createEduPhoto")
    fun  postCreateEduPhoto(@Body createEduImgData: CreateEduImgData): Call<CreateEduImgResult>
}

class UploadEduMarkActivity : AppCompatActivity() {

    var startX=200.0f
    var startY=200.0f
    var leftMargin = 0
    var topMargin = 0
    var imgWidth=0.0f
    var imgHeight=0.0f
    private var mBinding: ActivityMainBinding?=null
//    private val binding get()=mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_edu_mark)

        mark_box.bringToFront()
        mark_box1.bringToFront()
        mark_box2.bringToFront()

        val postID = intent.getStringExtra("postID").toString()
        val imgUrl = intent.getStringExtra("imgUrl")
        val voiceGuide = intent.getStringExtra("voiceGuide").toString()
        val textGuide = intent.getStringExtra("textGuide").toString()
        var currBox:Int = 0
        val currentImgUrl = intent.getStringExtra("imgUrl").toString()
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(currentImgUrl))
        imgView_screenImg.setImageBitmap(bitmap)

        val boxes:List<ImageView> = listOf(mark_box,mark_box1,mark_box2)

        Log.d("보이스~~~" , voiceGuide)

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

//        val storageRef = Firebase.storage.reference

        btn_next.setOnClickListener {
            val intent = Intent(this, EduImgListActivity::class.java)
            intent.putExtra("postID", postID)
            intent.putExtra("img", "")

            val CAPTURE_PATH = ""
            ActivityCompat.requestPermissions(this, arrayOf(permission.WRITE_EXTERNAL_STORAGE), 1)
            layout_imgMark?.buildDrawingCache()
            val captureView: Bitmap? = layout_imgMark?.drawToBitmap()
            val fos: FileOutputStream

//            val strFolderPath = Environment.getExternalStorageDirectory().absolutePath + CAPTURE_PATH
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
                    .child("/images/${imgTime}.png")
                    .putStream(stream)

                Log.d("###imgTime" , imgTime.toString())
                Log.d("###uploadtask" , uploadTask.toString())
                uploadTask.addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener {
                        Log.d("uri?", it.path.toString())
                        Log.d("uri????", it.toString())

                        var createEduImgData = CreateEduImgData(postID,it.toString(),voiceGuide,textGuide)
                        postApi(createEduImgData)

                        intent.putExtra("markedImg", strFilePath)
                        startActivity(intent)

//                        val file = File(it.path)
//                        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
//                        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)
//
//                        val storageRef = Firebase.storage.reference
//
//                        val fileRef = storageRef.child("images/${imgTime}.png")
//                        Log.d("zzz--imgTime" , imgTime.toString())
//                        val localFile = File.createTempFile("images", "png")
//                        fileRef.getFile(localFile)
//                            .addOnSuccessListener {
//                                // 파일 다운로드 성공
//                                val requestFile = RequestBody.create(MediaType.parse("image/*"), localFile)
//                                val imagePart = MultipartBody.Part.createFormData("image", localFile.name, requestFile)
//                                // 이미지 업로드 로직 작성
//                                Log.d("++++++", imagePart.toString())
//                                Log.d("aaaaa", requestFile.toString())
//                            }
//                            .addOnFailureListener {
//                                Log.e("TAG", "Error downloading file: ${it.message}")
//                            }
//
////                        postApi(postID,imagePart,voiceGuide,textGuide)
//                        Log.d(">>>//>>" , file.toString())
//                        Log.d(">>//>imgpart>>" , imagePart.toString())
                    }

                }
                    .addOnFailureListener { e ->
                        Log.d("nopenopenope" , e.message.toString())
                    }
                Log.d("***uploadTask" , uploadTask.toString())

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Log.d("왜 진짜 안되냐.." , e.toString())
            } finally {
                Toast.makeText(this, "COMPLETE", Toast.LENGTH_SHORT).show()
                Log.d("캡쳐 결과!!!!!1", strFilePath)

//                var postData = CreateEduImgData(postID,strFilePath,voiceGuide,textGuide)
//                postApi(postData)

                Log.d("포스트 아이디!!", postID)
                Log.d("텍스트 !!", voiceGuide)
                Log.d("^^^비트맵!!" , captureView.toString())

//                intent.putExtra("markedImg", strFilePath)
//                startActivity(intent)
            }

//            intent.putExtra("markedImg" , markedImg)
        }
    }

    private fun downloadImg (pathString:String) {
        val storageRef = Firebase.storage.reference
        val fileRef = storageRef.child(pathString)
        val localFile = File.createTempFile("images", "png")
        fileRef.getFile(localFile)
            .addOnSuccessListener {
                // 파일 다운로드 성공
                val requestFile = RequestBody.create(MediaType.parse("image/*"), localFile)
                val imagePart = MultipartBody.Part.createFormData("image", localFile.name, requestFile)
                // 이미지 업로드 로직 작성
            }
            .addOnFailureListener {
                // 파일 다운로드 실패
                Log.e("TAG", "Error downloading file: ${it.message}")
            }
    }
    private fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = c?.getString(index!!)
        return result!!
    }


    private fun handleMark(box: ImageView) {
        box.visibility =  View.VISIBLE
    }

    private fun postApi(createEduImgData:CreateEduImgData) {
        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(CreateEduImgApi::class.java)

        Log.d("왜 안되냐 ", "oh noooo")

//        service.postCreateEduPhoto(postID,imgUrl,voiceGuide,textGuide).enqueue(object : Callback<CreateEduImgResult> {
        service.postCreateEduPhoto(createEduImgData).enqueue(object : Callback<CreateEduImgResult> {
            override fun onResponse(call: Call<CreateEduImgResult>, response: Response<CreateEduImgResult>
            ) {
                Log.d("... result",response.toString())
                var result = response.body()?.success
                Log.d("postApi result",result.toString())
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