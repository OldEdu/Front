package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.oldedu.adapter.CommentAdapter
import com.example.oldedu.databinding.ActivityDetailBinding
import com.example.oldedu.databinding.ActivityEducated3Binding
import com.example.oldedu.model.Comment
import com.example.oldedu.model.CommentResponse
import com.example.oldedu.model.EduPhoto
import com.example.oldedu.model.EduPhotoResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add_edu_img.*
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.ArrayList

class Educated3 : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityEducated3Binding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!


    var requestQueue: RequestQueue?= null
    lateinit var commentList: ArrayList<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educated3)

        mBinding = ActivityEducated3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(applicationContext)

        val postID = intent.getStringExtra("postID")
        val title = intent.getStringExtra("title")
        val category=intent.getStringExtra("category")
        val scrapNum=intent.getStringExtra("scrapNum")
        val heartNum=intent.getStringExtra("heartNum")

        binding.TitleTextView.text=title
        binding.categoryTextView.text=category
        binding.heartNumTextView.text=heartNum
        binding.scrapNumTextView.text=scrapNum


        back_btn.setOnClickListener{
            val intent = Intent(this, educated2::class.java)
            startActivity(intent)
        }

        requestCommentList(postID.orEmpty());


    }

    private fun requestCommentList(postID:String){

        val url="http://34.168.110.14:8080/commentListPost/${postID}"
        //요청 객체만들기
        val request = object : StringRequest(
            //요청
            Request.Method.GET,
            url,
            //응답
            {
                //정상응답일때
                //Log.d("success:","응답->$it")
                processResponse(it)
            },
            {
                //에러일때
                Log.d("err","응답->${it.message}")
            }
        ){

        }

        //요청하고 응답받으면 동일한 주소로보내면 전에받았던걸 그대로보여줄수도있는데
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)

    }
    fun processResponse(response:String){
        val gson= Gson()
        val commentResponse = gson.fromJson(response, CommentResponse::class.java)

        commentList = commentResponse.commentList;

        binding.rvComment.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rvComment.setHasFixedSize(true)

        binding.rvComment.adapter = CommentAdapter(commentList)
    }





}