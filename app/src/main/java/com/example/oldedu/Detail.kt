package com.example.oldedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.oldedu.databinding.ActivityDetailBinding
import com.example.oldedu.model.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*


class Detail : AppCompatActivity(), TextToSpeech.OnInitListener {

    // 전역 변수로 바인딩 객체 선언
    lateinit var tts:TextToSpeech
    private var mBinding: ActivityDetailBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    private var currentPosition:Int = 0;

    var requestQueue: RequestQueue?= null
    lateinit var eduPhotoList:ArrayList<EduPhoto>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(applicationContext)

        val model = intent.getParcelableExtra<edu>("edumodel")

        val title = model?.title.orEmpty()
        val postId= model?.postID.orEmpty()
        val category = model?.category.orEmpty()
        val heartNum = model?.heart
        val scrapNum = model?.scrap

        binding.titleTextView.text = title

        requestEduPhotoList(model?.postID.orEmpty()); //교욱 사진 불러오기
        binding.btnBack.setOnClickListener{
            val intent = Intent(this, educated2::class.java)
            startActivity(intent)
        }
        binding.textGuideBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TextGuideActivity::class.java)

            intent.putExtra("textGuide", eduPhotoList[currentPosition].textGuide)
            startActivity(intent)
        })
        binding.voiceGuideBtn.setOnClickListener{
            //tts 설정
            tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
                if(it== TextToSpeech.SUCCESS){
                    tts.language= Locale.ENGLISH
                    tts.setSpeechRate(1.0f)
                    tts.speak(eduPhotoList[currentPosition].voiceGuide.toString(),TextToSpeech.QUEUE_ADD,null);
                }
            })
        }

        binding.btnBack.setOnClickListener{
            finish()
        }

        //채팅 버튼 clickListener
        binding.btnComment.setOnClickListener{
            val intent = Intent(this,Educated3::class.java)

            intent.putExtra("postID",postId ) //댓글 액티비티에 postID 값 넘겨줌
            intent.putExtra("title",  title) //댓글 액티비티에 title 값 넘겨줌
            intent.putExtra("category",category)//댓글 액티비티에 category 값 넘겨줌
            intent.putExtra("heartNum",heartNum.toString())//댓글 액티비티에 heartNum 값 넘겨줌
            intent.putExtra("scrapNum",scrapNum.toString())//댓글 액티비티에 scrapNum 값 넘겨줌

            startActivity(intent)
        }

        //채팅 버튼 clickListener
        binding.commentGoBtn.setOnClickListener{
            val intent = Intent(this,Educated3::class.java)

            intent.putExtra("postID",postId ) //댓글 액티비티에 postID 값 넘겨줌
            intent.putExtra("title",  title) //댓글 액티비티에 title 값 넘겨줌
            intent.putExtra("category",category)//댓글 액티비티에 category 값 넘겨줌
            intent.putExtra("heartNum",heartNum.toString())//댓글 액티비티에 heartNum 값 넘겨줌
            intent.putExtra("scrapNum",scrapNum.toString())//댓글 액티비티에 scrapNum 값 넘겨줌

            startActivity(intent)
        }


    }

    inner class CustomPagerAdapter: FragmentStateAdapter {
        private val PAGENUMBER = eduPhotoList.size-1;
        constructor(activity: FragmentActivity):super(activity)
        override fun getItemCount(): Int {
            return PAGENUMBER;
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                position -> {
                    PageFragment.newInstance("제목", eduPhotoList[position].eduPhotoID!!,
                        eduPhotoList[position].imgUrl!!, eduPhotoList[position].textGuide!!, eduPhotoList[position].voiceGuide!!,
                        eduPhotoList[position].imgNum)
                }


                else -> PageFragment.newInstance("제목", "",
                    "", "", "",
                    0)
            }
        }
    }
    private fun requestEduPhotoList(postID:String){

        val url="http://34.168.110.14:8080/eduPhoto/${postID}"
        //요청 객체만들기
        val request = object : StringRequest(
            //요청
            Request.Method.GET,
            url,
            //응답
            {
                //정상응답일때
                //Log.d("success:","응답->$it")
                processResponse(it)
            },
            {
                //에러일때
                Log.d("err","응답->${it.message}")
            }
        ){

        }

        //요청하고 응답받으면 동일한 주소로보내면 전에받았던걸 그대로보여줄수도있는데
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)

    }
    fun processResponse(response:String){
        val gson= Gson()
        val eduPhotoResponse = gson.fromJson(response, EduPhotoResponse::class.java)

        eduPhotoList = eduPhotoResponse.eduPhotoList;

        //뷰페이저 생성 및 할당
        viewpager.adapter =CustomPagerAdapter(this)
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.offscreenPageLimit = eduPhotoList.size-1


        indicator.setViewPager(viewpager)
        indicator.createIndicators(eduPhotoList.size-1,0)


        viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.animatePageSelected(position)
                currentPosition=position;
            }
        })
    }

    override fun onInit(status: Int) {
        TODO("Not yet implemented")
    }

}