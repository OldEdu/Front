package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.oldedu.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.studentbutton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Do you want Login?")    // 제목
            val itemList = arrayOf<String>("Login", "No Login")    // 항목 리스트
            // 항목 클릭 시 이벤트
            builder.setItems(itemList) { dialog, which ->
                binding.textView5.text = "${itemList[which]}이 선택되었습니다"

            }

            builder.show()
        }

        teacherbutton.setOnClickListener({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })
    }
}


