package com.example.oldedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oldedu.adapter.eduadapter
import com.example.oldedu.databinding.ActivityEdu1LifestyleBinding
import com.example.oldedu.databinding.ActivityEdu1TransportBinding
import com.example.oldedu.educated.edu_lifestyle
import com.example.oldedu.educated.edu_transport
import com.example.oldedu.model.dto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class edu1_transport : AppCompatActivity() {

    private lateinit var binding: ActivityEdu1TransportBinding
    private lateinit var adapter: eduadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEdu1TransportBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initview()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.168.110.14:8080/heartPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val edu_transport = retrofit.create(edu_transport::class.java)

        edu_transport.getpost()
            .enqueue(object : Callback<dto> {
                override fun onResponse(call: Call<dto>, response: Response<dto>) {
                    if (!response.isSuccessful){
                        return
                    }
                    response.body()?.let{
                        Log.d(edu1_lifestyle.Tag,it.toString())

                        it.result.forEach{edu->
                            Log.d(edu1_lifestyle.Tag,edu.toString())
                        }
                        adapter.submitList(it.result)
                    }
                }

                override fun onFailure(call: Call<dto>, t: Throwable) {

                }

            })
    }
    fun initview(){
        adapter = eduadapter()

        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter
    }
    companion object{
        private const val Tag = "transport"
    }
}