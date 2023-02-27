package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oldedu.databinding.ActivityTxtdetailBinding
import com.example.oldedu.model.txt
import com.example.oldedu.txtdetail
import kotlinx.android.synthetic.main.activity_detail.*

class txtdetail : AppCompatActivity() {

    private lateinit var binding: ActivityTxtdetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTxtdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = intent.getParcelableExtra<txt>("txtmodel")



    }
}