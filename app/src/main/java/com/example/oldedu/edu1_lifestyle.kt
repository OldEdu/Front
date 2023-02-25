package com.example.oldedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.oldedu.adapter.eduadapter
import com.example.oldedu.databinding.ActivityEdu1LifestyleBinding
import com.example.oldedu.educated.edu_lifestyle
import com.example.oldedu.educated.edu_lifestyle2
import com.example.oldedu.educated.edu_lifestyle3
import com.example.oldedu.educated.edu_transport3
import com.example.oldedu.model.dto
import com.example.oldedu.model.searchdto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class edu1_lifestyle : AppCompatActivity() {

    private lateinit var binding: ActivityEdu1LifestyleBinding
    private lateinit var adapter: eduadapter
    private lateinit var edu_lifestyle1: edu_lifestyle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdu1LifestyleBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initview()

        val retrofit1 = Retrofit.Builder()
            .baseUrl("http://34.168.110.14:8080/searchRecentPosts/")
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

        edu_lifestyle1 = retrofit1.create(edu_lifestyle::class.java)
        val edu_lifestyle2 = retrofit2.create(edu_lifestyle2::class.java)
        val edu_lifestyle3 = retrofit3.create(edu_lifestyle3::class.java)


        edu_lifestyle1.getpost1("")
            .enqueue(object : Callback<searchdto>{
                override fun onResponse(call: Call<searchdto>, response: Response<searchdto>) {
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

                override fun onFailure(call: Call<searchdto>, t: Throwable) {

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
        binding.searchBar.setOnKeyListener { v, keyCode, event ->
            if(keyCode== KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                search(binding.searchBar.text.toString())
                return@setOnKeyListener true
            }

            return@setOnKeyListener false
        }

    }
    private fun search(keyword: String){
        edu_lifestyle1.getpost1(keyword)

            .enqueue(object : Callback<searchdto>{
                override fun onResponse(call: Call<searchdto>, response: Response<searchdto>) {
                    if (!response.isSuccessful){
                        return
                    }
                    adapter.submitList(response.body()?.result.orEmpty())

                }

                override fun onFailure(call: Call<searchdto>, t: Throwable) {

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