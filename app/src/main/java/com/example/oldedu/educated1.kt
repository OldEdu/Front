package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mypage_teacher.*
import kotlinx.android.synthetic.main.activity_mypage_teacher.btn_back



class educated1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educated1)

        home.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        btn_back.setOnClickListener({
            val intent = Intent(this, CatActivity::class.java)
            startActivity(intent)
        })




    }
}