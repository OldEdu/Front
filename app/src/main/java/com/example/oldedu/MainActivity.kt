package com.example.oldedu

import android.content.DialogInterface
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


        teacherbutton.setOnClickListener({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })


        studentbutton.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Login")
                .setMessage("Do you want to Login?")
                .setPositiveButton("yes",
                DialogInterface.OnClickListener{dialog, id-> var resultText = "확인"
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)})
                .setNegativeButton("no",
                    DialogInterface.OnClickListener{dialog, id-> var resultText = "취소"
                        val intent = Intent(this, CatActivity::class.java)
                        startActivity(intent)})
            builder.show()





        }



    }
}


