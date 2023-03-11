package com.example.oldedu

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.oldedu.adapter.CommentAdapter
import com.example.oldedu.databinding.ActivityEducated3Binding
import com.example.oldedu.model.Comment
import com.example.oldedu.model.CommentResponse
import com.example.oldedu.model.edu
import com.google.gson.Gson


class Educated3 : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityEducated3Binding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var heartChanged=false;
    var requestQueue: RequestQueue?= null
    lateinit var eduPost:edu
    lateinit var commentList: ArrayList<Comment>
    var scaleAnimation: ScaleAnimation? = null
    var bounceInterpolator //애니메이션이 일어나는 동안의 회수, 속도를 조절하거나 시작과 종료시의 효과를 추가 할 수 있다
            : BounceInterpolator? = null


    var postID:String? =""
    var title :String?=""
    var category:String?=""
    var scrapNum:String?=""
    var heartNum:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educated3)

        mBinding = ActivityEducated3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(applicationContext)

        postID = intent.getStringExtra("postID")
        title = intent.getStringExtra("title")
        category=intent.getStringExtra("category")
        scrapNum=intent.getStringExtra("scrapNum")
        heartNum=intent.getStringExtra("heartNum")

        binding.TitleTextView.text=title
        binding.categoryTextView.text=category
        binding.heartNumTextView.text=heartNum
        binding.scrapNumTextView.text=scrapNum

        scaleAnimation = ScaleAnimation(
            0.7f,
            1.0f,
            0.7f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
            Animation.RELATIVE_TO_SELF,
            0.7f
        )

        scaleAnimation!!.duration = 500
        bounceInterpolator = BounceInterpolator()
        scaleAnimation!!.interpolator = bounceInterpolator

        binding.backBtn.setOnClickListener{
            if(heartChanged){
                val intent = Intent(this, Detail::class.java)
                intent.putExtra("edumodel",eduPost);
                startActivity(intent)
            }else{
                finish()
            }

        }

        requestCommentList(postID.orEmpty());

        //게시물 하트 수 올리기 기능
        binding.buttonFavorite.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (LoginActivity.userID=="" ){   //로그인 하지 않았을 경우
                binding.buttonFavorite.isChecked=false
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Please login and Up Heart.")
                    .setMessage("Do you want to login?")
                    .setPositiveButton("yes",
                        DialogInterface.OnClickListener{dialog, id->
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)})
                    .setNegativeButton("no",
                        DialogInterface.OnClickListener { dialog, id ->

                        })
                // 다이얼로그를 띄워주기
                builder.show()
            }
            else{
                compoundButton.startAnimation(scaleAnimation)
                if (postID != null) {
                    requestUpHeart(postID.orEmpty())
                    heartChanged=true
                }
            }


        }
        //게시물 스크랩 기능
        binding.buttonScrap.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (LoginActivity.userID=="" ){   //로그인 하지 않았을 경우
                binding.buttonScrap.isChecked=false
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Please login and Save post.")
                    .setMessage("Do you want to login?")
                    .setPositiveButton("yes",
                        DialogInterface.OnClickListener{dialog, id->
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)})
                    .setNegativeButton("no",
                        DialogInterface.OnClickListener { dialog, id ->

                        })
                // 다이얼로그를 띄워주기
                builder.show()
            }


            else if (LoginActivity.userType==false ){   //선생님으로 로그인 했을 경우
                binding.buttonScrap.isChecked=false
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Only students can do the scraps.") //학생만 스크랩 할 수 있다고 안내
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                // 다이얼로그를 띄워주기
                builder.show()
            }
            else if(!binding.buttonScrap.isChecked){
                compoundButton.startAnimation(scaleAnimation)
                if (postID != null) {
                    requestSavePost(postID.orEmpty(),LoginActivity.userID)
                }
            }
            else if(binding.buttonScrap.isChecked){
                compoundButton.startAnimation(scaleAnimation)
                if (postID != null) {
                    //TODO
                //requestSaveCancelPost(postID.orEmpty(),LoginActivity.userID)
                }
            }
        }



        //댓글 작성하기 기능
        binding.commentSendButton.setOnClickListener{
            val commentContent=binding.commentEditText.text.toString()
            if (LoginActivity.userID=="" ){   //로그인 하지 않았을 경우

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Please login and enter your comment.")
                    .setMessage("Do you want to login?")
                    .setPositiveButton("yes",
                        DialogInterface.OnClickListener{dialog, id->
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)})
                    .setNegativeButton("no",
                        DialogInterface.OnClickListener { dialog, id ->

                        })
                // 다이얼로그를 띄워주기
                builder.show()
            }

            else if(commentContent==""){ //댓글의 글자를 입력하지 않았을 경우
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Please enter your comment")
                    .setMessage("Enter at least 10 characters")
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener{dialog, id->
                           })
                // 다이얼로그를 띄워주기
                builder.show()
            }
            else{
                sendComment(postID.orEmpty(),LoginActivity.userID, commentContent)
            }
        }


    }


    private fun sendComment(postID:String,userID:String,comt_content:String){
        val url="http://34.168.110.14:8080/writeComment"
        // 2. Request Obejct인 StringRequest 생성
        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, "댓글 작성 완료", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["postID"] = postID
                params["userID"] = userID
                params["comt_content"] = comt_content // 로그인하는 휴대폰번호 정보

                return params
            }

        }
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)
    }
    private fun requestUpHeart(postID:String){

        val url="http://34.168.110.14:8080/upPostHeart/${postID}"
        //요청 객체만들기
        val request = object : StringRequest(
            //요청
            Request.Method.GET,
            url,
            //응답
            {
                //정상응답일때

                //Log.d("success:","응답->$it")                processResponse(it)

                Log.d("success:","응답->$it")
                eduPostProcessResponse(it)


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

    private fun requestSavePost(postID:String,userID:String){

        val url="http://34.168.110.14:8080/scrap"
        //요청 객체만들기
        // 2. Request Obejct인 StringRequest 생성
        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
                val scrapInt=(scrapNum?.toInt())
                if (scrapInt != null) {
                    binding.scrapNumTextView.text=(scrapInt+1).toString()
                }
                              },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["postID"] = postID
                params["userID"] = userID

                return params
            }

        }
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)

    }


    private fun requestCommentList(postID:String){

        val url="http://34.168.110.14:8080/commentListPost/${postID}"
        //요청 객체만들기
        val request = object : StringRequest(
            //요청
            Request.Method.GET,
            url,
            //응답
            {
                //정상응답일때
                //Log.d("success:","응답->$it")
                commentListProcessResponse(it)
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
    fun commentListProcessResponse(response:String){
        val gson= Gson()
        val commentResponse = gson.fromJson(response, CommentResponse::class.java)

        commentList = commentResponse.commentList;

        binding.rvComment.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rvComment.setHasFixedSize(true)

        binding.rvComment.adapter = CommentAdapter(commentList)
    }

    fun eduPostProcessResponse(response:String){
        val gson= Gson()
        val eduPostResponse = gson.fromJson(response, edu::class.java)
        eduPost=eduPostResponse
        binding.heartNumTextView.text=eduPostResponse.heart.toString()
    }







}