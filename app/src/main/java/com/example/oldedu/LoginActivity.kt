package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignUp = findViewById<Button>(R.id.btn_signUp)

        fun moveToSignupPage() {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener{moveToSignupPage()}
    }
}