package com.example.oldedu.modifyPhoto
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.oldedu.R
import com.example.oldedu.databinding.ActivityModifyUploadEduMarkBinding
import kotlinx.android.synthetic.main.activity_add_edu_img.*
import kotlinx.android.synthetic.main.activity_upload_edu_text.*
import kotlinx.android.synthetic.main.activity_upload_edu_text.imgView_screenImg

class ModifyUploadEduTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_edu_text)

        val imgUrl = intent.getStringExtra("imgUrl")
        val eduPhotoID = intent.getStringExtra("eduPhotoID")
        val textGuide = intent.getStringExtra("textGuide")
        val voiceGuide = intent.getStringExtra("voiceGuide")

        editText_voice.setText(voiceGuide)
        editText_text.setText(textGuide)

        val currentImgUrl = intent.getStringExtra("imgUrl").toString()
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(currentImgUrl))
        imgView_screenImg.setImageBitmap(bitmap)

        btn_next.setOnClickListener {
            val intent = Intent(this, ModifyUploadEduMarkActivity::class.java)
            intent.apply {
                this.putExtra("eduPhotoID",eduPhotoID)
                this.putExtra("imgUrl",imgUrl)
                this.putExtra("voiceGuide",editText_voice.text.toString())
                this.putExtra("textGuide",editText_text.text.toString())
            }
            startActivity(intent)
        }
    }
}