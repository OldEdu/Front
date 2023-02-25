package com.example.oldedu


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_educated2.*


class educated2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educated2)

        finishbtn.setOnClickListener({
            val intent = Intent(this, educated3::class.java)
            startActivity(intent)
        })
    }
}