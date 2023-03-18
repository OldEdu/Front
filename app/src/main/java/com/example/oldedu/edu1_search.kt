package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oldedu.adapter.eduadapter
import com.example.oldedu.databinding.ActivityEdu1SearchBinding
import com.example.oldedu.educated.edu_search
import com.example.oldedu.educated.edu_search2
import com.example.oldedu.educated.edu_search3
import com.example.oldedu.model.dto
import com.example.oldedu.model.searchdto
import kotlinx.android.synthetic.main.activity_edu1_economic.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class edu1_search : AppCompatActivity() {

    private lateinit var binding: ActivityEdu1SearchBinding
    private lateinit var adapter: eduadapter
    private lateinit var Edu_search: edu_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEdu1SearchBinding.inflate(layoutInflater)

        setContentView(binding.root)


        initview()

        val retrofit1 = Retrofit.Builder()
            .baseUrl("http://34.168.110.14:8080/searchHeartPosts/")
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

        Edu_search = retrofit1.create(edu_search::class.java)
        val edu_search2 = retrofit2.create(edu_search2::class.java)
        val edu_search3 = retrofit3.create(edu_search3::class.java)

        heartbtn.setOnClickListener {
            Edu_search.getpost1("")
                .enqueue(object : Callback<searchdto> {
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
        }

       viewbtn.setOnClickListener {
           edu_search2.getpost()
               .enqueue(object : Callback<dto> {
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

        recentbtn.setOnClickListener {
            edu_search3.getpost()
                .enqueue(object : Callback<dto> {
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
        edu_search3.getpost()
            .enqueue(object : Callback<dto> {
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
    private fun search(keyword:String){
        Edu_search.getpost1(keyword)
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
        adapter = eduadapter(itemClickedListener = {
            val intent = Intent(this, Detail::class.java)
            intent.putExtra("edumodel",it)
            startActivity(intent)
        })

        binding.edulist.layoutManager = LinearLayoutManager(this)
        binding.edulist.adapter = adapter

    }
    companion object{
        private const val Tag = "edu1_search"
    }
}