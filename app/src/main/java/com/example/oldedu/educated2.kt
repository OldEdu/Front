package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oldedu.adapter.eduadapter
import com.example.oldedu.adapter.txtadapter
import com.example.oldedu.databinding.ActivityEducated2Binding
import com.example.oldedu.educated.edu_txt
import com.example.oldedu.model.txtdto
import kotlinx.android.synthetic.main.activity_educated2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class educated2 : AppCompatActivity() {

    private lateinit var binding: ActivityEducated2Binding
    private lateinit var adapter: txtadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEducated2Binding.inflate(layoutInflater)

        setContentView(binding.root)
        txtview()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.168.110.14:8080/eduPhoto/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val edu_txt = retrofit.create(edu_txt::class.java)


        edu_txt.getpost()
            .enqueue(object : Callback<txtdto> {
                override fun onResponse(call: Call<txtdto>, response: Response<txtdto>) {
                    if (!response.isSuccessful){
                        Log.e(Tag, "no txt")
                        return
                    }
                    response.body()?.let{
                        Log.d(Tag,it.toString())

                        it.result.forEach{txt->
                            Log.d(Tag,txt.toString())
                        }
                        adapter.submitList(it.result)
                    }
                }

                override fun onFailure(call: Call<txtdto>, t: Throwable) {

                }

            })

        finishbtn.setOnClickListener({
                val intent = Intent(this, Educated3::class.java)
                startActivity(intent)
            })


    }
    fun txtview() {
        adapter = txtadapter()

        binding.txtlist.layoutManager = LinearLayoutManager(this)
        binding.txtlist.adapter = adapter
    }

    companion object{
        private const val Tag = "edu_txt"
    }
}