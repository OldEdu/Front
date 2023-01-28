package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val btnSignUpSubmit = findViewById<Button>(R.id.btn_signupSubmit)

        fun moveToSignupPage() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnSignUpSubmit.setOnClickListener{moveToSignupPage()}
    }
}