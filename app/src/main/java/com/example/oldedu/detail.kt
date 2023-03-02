package com.example.oldedu

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.oldedu.databinding.ActivityDetailBinding
import com.example.oldedu.model.edu
import com.example.oldedu.model.txt
import com.example.oldedu.model.voicedto
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Character.toString
import java.util.*


class detail : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var textToSpeech: TextToSpeech

    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = intent.getParcelableExtra<edu>("edumodel")

        binding.titletextview.text = model?.title.orEmpty()
        binding.detailview.text = model?.postID.orEmpty()

        val voicebtn: Button = findViewById(R.id.voicebtn)
        voicebtn.setOnClickListener{

            val intent: Intent = Intent()
            intent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
            activityResult.launch(intent)
        }


        txtbtn.setOnClickListener({
            val intent = Intent(this, educated2::class.java)
            startActivity(intent)
        })


    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

        if(it.resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
            textToSpeech = TextToSpeech(this,this)
        }else{
            val installIntent:Intent = Intent()
            installIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
            startActivity(installIntent)
        }
    }

    //texttospeech 엔지 초기화

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val languageStatus: Int = textToSpeech.setLanguage(Locale.ENGLISH)
            if(languageStatus==TextToSpeech.LANG_MISSING_DATA ||
                    languageStatus == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "we can't support you", Toast.LENGTH_LONG).show()

            }else{
                val data = intent.getParcelableExtra<txt>("txtmodel")

                binding.voicebtn.text = data?.voiceGuide.orEmpty()

                var speechStatus: Int = 0
                speechStatus = textToSpeech.speak(data.toString(), TextToSpeech.QUEUE_FLUSH,null,null)


                if(speechStatus == TextToSpeech.ERROR){
                    Toast.makeText(this, "we can't support you", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(this, "we can't support you", Toast.LENGTH_LONG).show()
        }

    }
}