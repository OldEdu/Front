package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add.*

class CatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat)

        btn_home.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        back.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }
}