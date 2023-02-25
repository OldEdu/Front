package com.example.oldedu

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import kotlinx.android.synthetic.main.activity_add_edu_img.*
import kotlinx.android.synthetic.main.activity_upload_edu_text.*
import kotlinx.android.synthetic.main.activity_upload_edu_text.imgView_screenImg

class UploadEduTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_edu_text)

        val postID = intent.getStringExtra("postID")
        val imgUrl = intent.getStringExtra("imgUrl")

//        var voiceGuide = editText_voice.text.toString()
//        var textGuide = editText_text.text.toString()

        val currentImgUrl = intent.getStringExtra("imgUrl").toString()
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(currentImgUrl))
        imgView_screenImg.setImageBitmap(bitmap)

        btn_next.setOnClickListener {
            val intent = Intent(this,UploadEduMarkActivity::class.java)
            intent.apply {
                this.putExtra("postID",postID)
                this.putExtra("imgUrl",imgUrl)
                this.putExtra("voiceGuide",editText_voice.text.toString())
                this.putExtra("textGuide",editText_text.text.toString())
            }
            startActivity(intent)
        }
    }
}