package com.example.oldedu

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import kotlinx.android.synthetic.main.activity_add_edu_img.*
import kotlinx.android.synthetic.main.activity_add_edu_img.imgView_screenImg
import kotlinx.android.synthetic.main.activity_upload_edu_img.*

class AddEduImgActivity : AppCompatActivity(), FragAddEduImgText.OnDataPassListener ,FragAddEduImgMark.OnDataPassListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edu_img)
        setFrag(0)
        btn_text.setOnClickListener { setFrag(0) }
        btn_mark.setOnClickListener { setFrag(1) }

        val currentImgUrl = intent.getStringExtra("imgUrl").toString()
        val postID = intent.getStringExtra("postID")
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(currentImgUrl))
        imgView_screenImg.setImageBitmap(bitmap)

        var postText:String = "" ;

    }

    private fun setFrag(fragNum:Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum) {
            0 -> {
                ft.replace(R.id.fragment_addImg,FragAddEduImgText()).commit()
            }
            1 -> {
                ft.replace(R.id.fragment_addImg,FragAddEduImgMark()).commit()
            }
        }
    }

    override fun onDataPass(voice: String, text: String) {
        Log.d(">>>VOICE!!!<<<<<<<<<<", voice)
        Log.d(">>>text!!!<<<<<<<<<<", text)
    }

    override fun onDataPass(markX: Int, markY: Int) {
        Log.d("******MARK X********", markX.toString())
        Log.d("******MARK Y********", markY.toString())
    }
}