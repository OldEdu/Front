package com.example.oldedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.oldedu.adapter.eduadapter
import com.example.oldedu.databinding.ActivityEdu1LifestyleBinding
import com.example.oldedu.educated.edu_lifestyle
import com.example.oldedu.educated.edu_lifestyle2
import com.example.oldedu.educated.edu_lifestyle3
import com.example.oldedu.educated.edu_transport3
import com.example.oldedu.model.dto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class edu1_lifestyle : AppCompatActivity() {

    private lateinit var binding: ActivityEdu1LifestyleBinding
    private lateinit var adapter: eduadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdu1LifestyleBinding.inflate(layoutInflater)

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

        val edu_lifestyle = retrofit1.create(edu_lifestyle::class.java)
        val edu_lifestyle2 = retrofit2.create(edu_lifestyle2::class.java)
        val edu_lifestyle3 = retrofit3.create(edu_lifestyle3::class.java)


        edu_lifestyle.getpost()
            .enqueue(object : Callback<dto>{
                override fun onResponse(call: Call<dto>, response: Response<dto>) {
                    if (!response.isSuccessful){
                        return
                    }
                    response.body()?.let{
                        Log.d(Tag,it.toString())

                        it.result.forEach{edu->
                            Log.d(Tag,edu.toString())
                        }
                        adapter.submitList(it.result)
                    }
                }

                override fun onFailure(call: Call<dto>, t: Throwable) {

                }

            })
        edu_lifestyle2.getpost()
            .enqueue(object : Callback<dto>{
                override fun onResponse(call: Call<dto>, response: Response<dto>) {
                    if (!response.isSuccessful){
                        return
                    }
                    response.body()?.let{
                        Log.d(Tag,it.toString())

                        it.result.forEach{edu->
                            Log.d(Tag,edu.toString())
                        }
                        adapter.submitList(it.result)
                    }
                }

                override fun onFailure(call: Call<dto>, t: Throwable) {

                }

            })
        edu_lifestyle3.getpost()
            .enqueue(object : Callback<dto>{
                override fun onResponse(call: Call<dto>, response: Response<dto>) {
                    if (!response.isSuccessful){
                        return
                    }
                    response.body()?.let{
                        Log.d(Tag,it.toString())

                        it.result.forEach{edu->
                            Log.d(Tag,edu.toString())
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
        const val Tag = "edu1_lifestyle"
    }
}