package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oldedu.LoginActivity.Companion.userID
import kotlinx.android.synthetic.main.activity_add.btn_home
import kotlinx.android.synthetic.main.activity_cat.*

class CatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cat)

        val userID = intent.getStringExtra("userID")

        btn_mypage.setOnClickListener({
            val intent = Intent(this, MypageStudentActivity::class.java)
            intent.putExtra("userID",userID)
            startActivity(intent)
        })

        btn_back.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        transportation.setOnClickListener({
            val intent = Intent(this, edu1_transport::class.java)
            startActivity(intent)
        })

        searching.setOnClickListener({
            val intent = Intent(this, edu1_search::class.java)
            startActivity(intent)
        })

        economic.setOnClickListener({
            val intent = Intent(this, edu1_economic::class.java)
            startActivity(intent)
        })

        lifestyle.setOnClickListener({
            val intent = Intent(this, edu1_lifestyle::class.java)
            startActivity(intent)
        })


    }
}