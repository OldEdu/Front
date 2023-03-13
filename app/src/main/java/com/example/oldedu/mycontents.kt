package com.example.oldedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.oldedu.adapter.ContentsAdapter
import com.example.oldedu.databinding.ActivityMycontentsBinding
import com.example.oldedu.model.Mycontents
import com.example.oldedu.model.MycontentsResponse
import com.google.gson.Gson

class mycontents : AppCompatActivity() {

    private var mbinding:ActivityMycontentsBinding? = null
    private val binding get() = mbinding!!

    var requestQueue: RequestQueue?= null
    lateinit var contentsList: ArrayList<Mycontents>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycontents)

        mbinding = ActivityMycontentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(applicationContext)
        val userID = intent.getStringExtra("userID")
        val title = intent.getStringExtra("title")
        val postID = intent.getStringExtra("postID")


        requestContentsList(userID.orEmpty());
        requestContentsList(postID.orEmpty());


    }

    private fun requestContentsList(userID:String){
        val url="http://34.168.110.14:8080/myPost/${userID}"
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

    fun processResponse(response:String) {

        val gson= Gson()
        val contentsResponse = gson.fromJson(response, MycontentsResponse::class.java)

        contentsList = contentsResponse.contentsList;



        binding.rvContents.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.rvContents.setHasFixedSize(true)
        binding.rvContents.adapter = ContentsAdapter(contentsList)


    }

}