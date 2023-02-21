package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edu_img_list.*

class EduImgListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edu_img_list)
        var postID = intent.getStringExtra("postID")
        btn_createImg.setOnClickListener {
            val intent = Intent(this,UploadEduImgActivity::class.java)
            intent.putExtra("postID",postID)
            startActivity(intent)
        }
    }
}