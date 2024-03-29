package com.example.oldedu
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.oldedu.modifyPhoto.ModifyUploadEduImgActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    companion object {
        var userID:String=""
        var userType:Boolean?=true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        test.setOnClickListener {
//            val testIntent = Intent(this,ModifyUploadEduImgActivity::class.java)
//            intent.putExtra("eduPhotoID" , "hYUdhWYPlgIgMuCcqlKI")
//            startActivity(testIntent)
//        }

//        Glide.with(this)
//            .load("https://firebasestorage.googleapis.com/v0/b/oldedu-c93f3.appspot.com/o/images%2F1677952570720.png?alt=media&token=60c6cbb1-461d-4f4d-b2bf-2cbd1d77c529")
//            .into(imgtest)

        btn_home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        var retrofit = Retrofit.Builder()
            .baseUrl("http://34.168.110.14:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService = retrofit.create(LoginService::class.java)

//        val btnSignUp = findViewById<Button>(R.id.btn_signUp)

        fun moveToSignupPage() {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        fun moveToMyPage(type : Boolean?, userID : String) {
            LoginActivity.userID=userID
            userType = type

            if (type==true) { // 학생일 경우
                val intent = Intent(this, CatActivity::class.java)
                intent.putExtra("userID", userID)
                startActivity(intent)
            } else { // 선생님일 경우
                val intent = Intent(this, MypageTeacherActivity::class.java)
                intent.putExtra("userID", userID)
                startActivity(intent)
            }
        }

        btn_signUp.setOnClickListener{moveToSignupPage()}

        btn_loginSubmit.setOnClickListener{
            var id = edit_id.text.toString()
            var pw = edit_pw.text.toString()

            loginService.requestLogin(id,pw).enqueue(object : Callback<Login>{
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    // success
                    var login = response.body()
                    var dialog = AlertDialog.Builder(this@LoginActivity)
                    if (login?.success==true) {
//                        dialog.setMessage("로그인 성공!")
                        moveToMyPage(login.userType, id)
                    } else {
                    Log.d("LOGIN","success : "+login?.success.toString())
                    Log.d("LOGIN","msg : "+login?.msg)

                    dialog.setTitle(login?.success.toString())
                    dialog.setMessage(login?.msg)
                    }
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