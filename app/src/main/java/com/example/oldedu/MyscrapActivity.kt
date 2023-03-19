package com.example.oldedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.oldedu.adapter.ScrapAdapter
import com.example.oldedu.databinding.ActivityMyscrapBinding
import com.example.oldedu.model.MycontentsResponse
import com.example.oldedu.model.Scrap
import com.example.oldedu.model.ScrapResponse
import com.google.gson.Gson

class MyscrapActivity : AppCompatActivity() {

    private var mbinding:ActivityMyscrapBinding? = null
    private val binding get() = mbinding!!

    var requestQueue: RequestQueue?= null
    lateinit var scrapList: ArrayList<Scrap>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myscrap)

        mbinding = ActivityMyscrapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(applicationContext)
        val userID = intent.getStringExtra("userID")
        val title = intent.getStringExtra("title")
        val postID = intent.getStringExtra("postID")
        val category = intent.getStringExtra("category")


        requestContentsList(userID.orEmpty());
        requestContentsList(postID.orEmpty());
        requestContentsList(title.orEmpty());
        requestContentsList(category.orEmpty());

    }

    private fun requestContentsList(userID:String){
        val url = "http://34.168.110.14:8080/getScrapList/${userID}"
        val request = object : StringRequest(
            Request.Method.GET,
            url,
            {
                processResponse(it)

            },
            {
                Log.d("err","응답->${it.message}")
            }
        ){

        }

        request.setShouldCache(false)
        requestQueue?.add(request)

    }

    fun processResponse(response:String){
        val gson = Gson()
        val scrapResponse = gson.fromJson(response, ScrapResponse::class.java)

        scrapList = scrapResponse.contentsList;

        binding.rvScrap.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.rvScrap.setHasFixedSize(true)
        binding.rvScrap.adapter = ScrapAdapter(scrapList)

    }

}