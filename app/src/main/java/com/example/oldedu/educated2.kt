package com.example.oldedu

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.back
import kotlinx.android.synthetic.main.activity_add.btn_home
import kotlinx.android.synthetic.main.activity_educated2.*
import kotlinx.android.synthetic.main.activity_main.*

class educated2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educated2)

        btn_home.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        back.setOnClickListener({
            val intent = Intent(this, educated1::class.java)
            startActivity(intent)
        })

        btn_txt.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Login")
                .setMessage("Do you want to Login?")
                .setPositiveButton("yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        var resultText = "확인"
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    })
                .setNegativeButton("no",
                    DialogInterface.OnClickListener { dialog, id ->
                        var resultText = "취소"

                    })
            builder.show()
        }
    }
}