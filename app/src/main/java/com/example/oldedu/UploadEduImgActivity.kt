package com.example.oldedu

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_upload_edu_img.*

class UploadEduImgActivity : AppCompatActivity() {
    private val OPEN_GALLERY = 1
    var currentImgUrl:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_edu_img)

        val postID = intent.getStringExtra("postID")
        val userID = intent.getStringExtra("userID")

        btn_addImg.setOnClickListener { openGallery() }
        btn_deleteImg.setOnClickListener { handleDelImgBtn() }
        btn_createPost.setOnClickListener {
            if(!currentImgUrl.toString().isNullOrBlank()) {
                val intent = Intent(this,UploadEduTextActivity::class.java)
                intent.putExtra("imgUrl",currentImgUrl.toString())
                intent.putExtra("postID",postID)
                intent.putExtra("userID",userID)
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