package com.example.oldedu

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_mypage_student.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.back
import kotlinx.android.synthetic.main.activity_signup.btn_home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

data class Register(
    @SerializedName("success")
    val success: Boolean ,
)

data class UserInfo(
    @SerializedName("userID")
    val userID: String ,

    @SerializedName("psword")
    val psword: String ,

    @SerializedName("userName")
    val userName: String ,

    @SerializedName("userType")
    val userType: Boolean
)

interface RegisterApi {
    @POST("register")
    fun  postUserRegister (@Body userInfo:UserInfo): Call<Register>
}


class SignupActivity : AppCompatActivity() {
//    FirebaseApp.initalizesApp(Context)

    lateinit var auth: FirebaseAuth
    var verificationId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var phoneNum:String = "" ;
        var nickname:String = "" ;
        var password:String = "" ;
        var role:Boolean =false  ;
        var verified:Boolean = false ;
        var confirmPW:Boolean = false ;

        // retrofit 회원가입
        val retrofit = Retrofit.Builder().baseUrl("http://34.168.110.14:8080/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(RegisterApi::class.java)

        // 헤더
        btn_home.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        back.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        init()

//        fun handleTeaStu():Boolean { // teacher, student 구분
//            if(role=="student") {
//                return
//            }
//        }

        fun clickPhoneSendAuth() { // firebase 전화로 인증 코드 전송

            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) { }
                override fun onVerificationFailed(e: FirebaseException) {
                }
                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    this@SignupActivity.verificationId = verificationId
                }
            }

            var number = edit_signup_id.text.trim().toString()
            if (number.isNotEmpty()) {
                btn_code_confirm.isEnabled = true
                btn_code_confirm.setBackgroundColor(Color.parseColor("#2A3F8D"))
                btn_code_confirm.setTextColor(Color.parseColor("#ffffff"))
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(number)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
                auth.setLanguageCode("kr")
                phoneNum = number

            }else {
                Toast.makeText(this,"Please Enter Number",Toast.LENGTH_SHORT).show()
            }
        }

        fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) { // 인증 번호 입력 후 확인 버튼
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        verified = true
                        text_phone_confirmed.text = "confirmed"
                    }
                    else {
                        verified = false
                        text_phone_confirmed.text = "Please enter the authentication number again"
                    }
                }
        }

        btn_code_confirm.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(verificationId, edit_phone_verifying.text.toString())
            signInWithPhoneAuthCredential(credential)
        }


        fun moveToSignupPage() { // 회원가입 완료 (로그인 페이지로 이동)
            val userInfo = UserInfo(phoneNum,password, nickname,role)
            service.postUserRegister(userInfo).enqueue(object : Callback<Register> {
                override fun onResponse(
                    call: Call<Register>,
                    response: Response<Register>
                ) {
//                    var result : MyPageStudent? = response.body()
                    var result = response.body()
//                    textView_name.text = result?.userName

                }

                override fun onFailure(call: Call<Register>, t: Throwable) {
                    Log.d("retrofit","실패")
                }

            })
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
                if (data[position] == "student") {
                    role = true
                } else {
                    role = false
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 회원가입 완료 누르기
        btn_signupSubmit.setOnClickListener {
            nickname = edit_signup_nickname.text.toString()
            if (!edit_signup_pw.text.toString().isNullOrBlank() && edit_signup_pw.text.toString() == edit_signup_confirmPW.text.toString()) {
                password = edit_signup_pw.text.toString()
                confirmPW = true
            } else {
                confirmPW = false
            }

            if (!nickname.isNullOrBlank() && confirmPW==true && verified ) {
                moveToSignupPage()
//                test.text = role.toString()
            } else {
                if (edit_signup_pw.text.toString() != edit_signup_confirmPW.text.toString()){
                    text_pwnotmatch_warn.text = "The password doesn't match"
                } else {
                    text_pwnotmatch_warn.text = ""
                }
                Toast.makeText(this,"Failed to Register",Toast.LENGTH_SHORT).show()

            }
        }



    }




    private fun init() {
        auth = FirebaseAuth.getInstance()
    }


}

