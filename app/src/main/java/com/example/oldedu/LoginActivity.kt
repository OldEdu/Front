package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService = retrofit.create(LoginService::class.java)

//        val btnSignUp = findViewById<Button>(R.id.btn_signUp)

        fun moveToSignupPage() {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btn_signUp.setOnClickListener{moveToSignupPage()}

        btn_loginSubmit.setOnClickListener{
            var id = edit_id.text.toString()
            var pw = edit_pw.text.toString()

            loginService.requestLogin(id,pw).enqueue(object : Callback<Login>{
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    // success
                    var login = response.body()
                    Log.d("LOGIN","success : "+login?.success.toString())
                    Log.d("LOGIN","msg : "+login?.msg)
                    var dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle(login?.success.toString())
                    dialog.setMessage(login?.msg)
                    dialog.show()
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    // fail
                    Log.e("LOGIN",t.message.toString())
                    var dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }
            })
        }
    }
}