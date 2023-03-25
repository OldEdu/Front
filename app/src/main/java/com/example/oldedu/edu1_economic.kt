package com.example.oldedu

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oldedu.adapter.eduadapter
import com.example.oldedu.databinding.ActivityEdu1EconomicBinding
import com.example.oldedu.educated.*
import com.example.oldedu.model.dto
import com.example.oldedu.model.searchdto
import kotlinx.android.synthetic.main.activity_edu1_economic.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class edu1_economic : AppCompatActivity() {

    private lateinit var binding: ActivityEdu1EconomicBinding
    private lateinit var adapter: eduadapter
    private lateinit var Edu_economic:edu_economic

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEdu1EconomicBinding.inflate(layoutInflater)

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

        Edu_economic = retrofit1.create(edu_economic::class.java)
        val edu_economic2 = retrofit2.create(edu_economic2::class.java)
        val edu_economic3 = retrofit3.create(edu_economic3::class.java)


        //기본 값 최신순 게시글 정렬
        binding.viewbtn.setBackgroundColor(Color.parseColor("#2F3A8D"))
        binding.heartbtn.setBackgroundColor(Color.parseColor("#2F3A8D"))
        binding.recentbtn.setBackgroundColor(Color.parseColor("#9D9D9D"))


        binding.heartbtn.setOnClickListener{
            binding.viewbtn.setBackgroundColor(Color.parseColor("#2F3A8D"))
            binding.heartbtn.setBackgroundColor(Color.parseColor("#9D9D9D"))
            binding.recentbtn.setBackgroundColor(Color.parseColor("#2F3A8D"))
            Edu_economic.getpost1("")
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

        binding.viewbtn.setOnClickListener{
            binding.viewbtn.setBackgroundColor(Color.parseColor("#9D9D9D"))
            binding.heartbtn.setBackgroundColor(Color.parseColor("#2F3A8D"))
            binding.recentbtn.setBackgroundColor(Color.parseColor("#2F3A8D"))
            edu_economic2.getpost()
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


        binding.recentbtn.setOnClickListener{
            binding.viewbtn.setBackgroundColor(Color.parseColor("#2F3A8D"))
            binding.heartbtn.setBackgroundColor(Color.parseColor("#2F3A8D"))
            binding.recentbtn.setBackgroundColor(Color.parseColor("#9D9D9D"))
            edu_economic3.getpost()
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
        edu_economic3.getpost()
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
        btn_back.setOnClickListener({
            val intent = Intent(this, CatActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
    private fun search(keyword:String){
        Edu_economic.getpost1(keyword)
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
        private const val Tag = "edu1_economic"
    }
}