package com.example.oldedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oldedu.databinding.ActivityDetailBinding
import com.example.oldedu.model.edu

class detail : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = intent.getParcelableExtra<edu>("edomodel")

        binding.titletextview.text=model?.title.orEmpty()
        binding.detailview.text=model?.postID.orEmpty()
    }
}