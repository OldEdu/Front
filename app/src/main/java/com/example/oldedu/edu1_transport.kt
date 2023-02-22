package com.example.oldedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oldedu.adapter.eduadapter
import com.example.oldedu.databinding.ActivityEdu1TransportBinding
import com.example.oldedu.educated.edu_transport
import com.example.oldedu.educated.edu_transport2
import com.example.oldedu.educated.edu_transport3
import com.example.oldedu.model.dto
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class edu1_transport : AppCompatActivity() {

    private lateinit var binding: ActivityEdu1TransportBinding
    private lateinit var adapter: eduadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEdu1TransportBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initview()
        val retrofit1 = Retrofit.Builder()
            .baseUrl("http://34.168.110.14:8080/heartPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofit2 = Retrofit.Builder()
            .baseUrl("http://34.168.110.14:8080/viewsPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofit3 = Retrofit.Builder()
            .baseUrl("http://34.168.110.14:8080/recentPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val edu_transport = retrofit1.create(edu_transport::class.java)
        val edu_transport2 = retrofit2.create(edu_transport2::class.java)
        val edu_transport3 = retrofit3.create(edu_transport3::class.java)


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
        edu_transport2.getpost()
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

        edu_transport3.getpost()
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

        binding.heartlist.layoutManager = LinearLayoutManager(this)
        binding.heartlist.adapter = adapter

        binding.viewlist.layoutManager = LinearLayoutManager(this)
        binding.viewlist.adapter = adapter

        binding.recentlist.layoutManager = LinearLayoutManager(this)
        binding.recentlist.adapter = adapter
    }
    companion object{
        private const val Tag = "edu1_transport"
    }
}