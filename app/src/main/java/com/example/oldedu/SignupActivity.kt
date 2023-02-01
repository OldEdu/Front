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
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.concurrent.TimeUnit

class SignupActivity : AppCompatActivity() {
//    FirebaseApp.initalizesApp(Context)

//    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
//        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

//        val auth = FirebaseAuth.getInstance()
//        var vId = ""

//        fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//            auth.signInWithCredential(credential)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        //인증성공
//                        test.setText("auth suc")
//                    }
//                    else {
//                        //인증실패
//                        test.setText("auth fail")
//                    }
//                }
//        }

        // firebase 전화로 인증 코드 전송
        fun clickPhoneSendAuth() {
            test.setText("complete!")


//            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) { }
//                override fun onVerificationFailed(e: FirebaseException) {
//                }
//                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                    vId = verificationId
//                }
//            }
//
//            val optionsCompat =  PhoneAuthOptions.newBuilder(auth)
//                .setPhoneNumber("+821012345678")
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(this)
//                .setCallbacks(callbacks)
//                .build()
//            PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
//            auth.setLanguageCode("kr")
//
//            btn_code_confirm.isEnabled = true
//            btn_code_confirm.setBackgroundResource(R.color.purple_700)
//            btn_code_confirm.setOnClickListener {
//                val credential = PhoneAuthProvider.getCredential(vId, "EditText에 적은 인증번호값")
//                signInWithPhoneAuthCredential(credential)
//            }
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
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            fun moveToSignupPage() {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            btn_signupSubmit.setOnClickListener { moveToSignupPage() }

        }




    }

