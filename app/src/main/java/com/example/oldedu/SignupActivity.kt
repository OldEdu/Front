package com.example.oldedu

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.back
import kotlinx.android.synthetic.main.activity_signup.btn_home
import java.util.concurrent.TimeUnit

class SignupActivity : AppCompatActivity() {
//    FirebaseApp.initalizesApp(Context)

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btn_home.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        back.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        init()

        fun clickPhoneSendAuth() { // firebase 전화로 인증 코드 전송
            var number = edit_signup_id.text.trim().toString()
            if (number.isNotEmpty()) {
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(number)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }else {
                Toast.makeText(this,"Please Enter Number",Toast.LENGTH_SHORT)
            }
        }

        fun moveToSignupPage() { // 회원가입 완료 (로그인 페이지로 이동)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btn_certify.setOnClickListener { clickPhoneSendAuth() }

        var data = listOf("teacher", "student")

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        spinner_role.adapter = adapter
        spinner_role.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //position은 선택한 아이템의 위치를 넘겨주는 인자입니다.
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btn_signupSubmit.setOnClickListener { moveToSignupPage() }

    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "onVertificationFailed : ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVertificationFailed : ${e.toString()}")
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
         // Save verification ID and resending token so we can use them later

        }
    }
}

