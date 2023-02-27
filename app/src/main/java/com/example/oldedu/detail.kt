package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oldedu.databinding.ActivityDetailBinding
import com.example.oldedu.model.edu
import kotlinx.android.synthetic.main.activity_detail.*


class detail : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = intent.getParcelableExtra<edu>("edumodel")

        binding.titletextview.text = model?.title.orEmpty()
        binding.detailview.text = model?.postID.orEmpty()


        txtbtn.setOnClickListener({
            val intent = Intent(this, educated2::class.java)
            startActivity(intent)
        })


    }
}