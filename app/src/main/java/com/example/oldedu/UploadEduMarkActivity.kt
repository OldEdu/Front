package com.example.oldedu

import android.content.Context
import android.graphics.Canvas
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.activity_upload_edu_mark.*
import kotlinx.android.synthetic.main.activity_upload_edu_mark.imgView_screenImg

class UploadEduMarkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_edu_mark)

        val postID = intent.getStringExtra("postID")
        val imgUrl = intent.getStringExtra("imgUrl")
        val voiceGuide = intent.getStringExtra("voiceGuide")
        val textGuide = intent.getStringExtra("textGuide")
        val currentImgUrl = intent.getStringExtra("imgUrl").toString()
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(currentImgUrl))
        imgView_screenImg.setImageBitmap(bitmap)
    }
}

class CustomView @JvmOverloads constructor(context: Context,
                                           attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    // 뷰의 내용이 렌더링 될때 호출 됩니다
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 여기다가 원하는걸 그리면 됩니다 :)
    }
}