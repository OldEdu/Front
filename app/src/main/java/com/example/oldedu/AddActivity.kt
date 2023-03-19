package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val userID = intent.getStringExtra("userID").toString()
        Log.d("Add액티비티의 유저 아이디" , userID)

        btn_home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        back_btn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_create.setOnClickListener {
            val intent = Intent(this, UploadEduInfoActivity::class.java)
            intent.putExtra("userID",userID)
            startActivity(intent)
        }


    }
}