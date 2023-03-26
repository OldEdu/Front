package com.example.oldedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oldedu.databinding.ActivityTextguideBinding

class TextGuideActivity:AppCompatActivity() {
    private var mBinding: ActivityTextguideBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTextguideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textGuide = intent.getStringExtra("textGuide");

        binding.textGuideTextView.text=textGuide

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}