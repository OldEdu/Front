package com.example.oldedu

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.example.oldedu.model.*
import com.google.gson.Gson
import java.text.FieldPosition


class educated3 : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityEducated3Binding? = null
    init{
        instance = this
    }

    companion object{
        private var instance:educated3? = null
        fun getInstance(): educated3? {
            return instance
        }


    }
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    val binding get() = mBinding!!

    var heartOnClicked = false;
    var scrapOnClicked = false;
    var requestQueue: RequestQueue? = null
    lateinit var eduPost: edu
    lateinit var commentList: ArrayList<Comment>
    var scaleAnimation: ScaleAnimation? = null
    var bounceInterpolator //애니메이션이 일어나는 동안의 회수, 속도를 조절하거나 시작과 종료시의 효과를 추가 할 수 있다
            : BounceInterpolator? = null


    var postID: String? = ""
    var title: String? = ""
    var category: String? = ""
    var scrapNum: String? = ""
    var heartNum: String? = ""
    var scrapID: String? = ""
    var heartID: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_educated3)

        mBinding = ActivityEducated3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(applicationContext)

        postID = intent.getStringExtra("postID")
        title = intent.getStringExtra("title")
        category = intent.getStringExtra("category")
        scrapNum = intent.getStringExtra("scrapNum")
        heartNum = intent.getStringExtra("heartNum")

        binding.TitleTextView.text = title
        binding.categoryTextView.text = category
        binding.heartNumTextView.text = heartNum
        binding.scrapNumTextView.text = scrapNum


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

        if(LoginActivity.userID.length>0){
            //하트, 스크랩 상태 화면에 출력
            viewEduPostUserClicked(postID.orEmpty(), LoginActivity.userID)
        }


        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            if (heartOnClicked || scrapOnClicked) {
                val intent = Intent(this, Detail::class.java)
                intent.putExtra("edumodel", eduPost);
                startActivity(intent)
            } else {
                finish()
            }
        }

        if(LoginActivity.userID.length!=0){   //로그인 했을 경우
            //댓글 리스트 화면에 출력
            requestCommentListLogin(postID.orEmpty(),LoginActivity.userID);
        }else{  //로그인 하지 않았을 경우
            requestCommentList(postID.orEmpty())
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
        //게시물 하트 수 올리기 기능
        binding.buttonFavorite.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (LoginActivity.userID == "") {   //로그인 하지 않았을 경우
                binding.buttonFavorite.isChecked = false
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Please login and Up Heart.")
                    .setMessage("Do you want to login?")
                    .setPositiveButton("yes",
                        DialogInterface.OnClickListener { dialog, id ->
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        })
                    .setNegativeButton("no",
                        DialogInterface.OnClickListener { dialog, id ->

                        })
                // 다이얼로그를 띄워주기
                builder.show()
            } else if (LoginActivity.userType == false) {   //선생님으로 로그인 했을 경우
                binding.buttonFavorite.isChecked = false
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Only students can up heart.") //학생만 하트를 올릴 수 있다고 안내
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                // 다이얼로그를 띄워주기
                builder.show()
            } else if (isChecked) { //하트수 올리기 하기
                Log.d("checked", isChecked.toString())
                compoundButton.startAnimation(scaleAnimation)
                if (postID != null) {
                    requestUpHeart(postID.orEmpty(), LoginActivity.userID)
                }
            } else if (!isChecked) { //하트수 내리기
                Log.d("checked", isChecked.toString())
                compoundButton.startAnimation(scaleAnimation)
                if (postID != null) {
                    requestDownHeart(heartID.orEmpty())
                }
            }


        }
        //게시물 스크랩 기능
        binding.buttonScrap.setOnCheckedChangeListener { compoundButton, isChecked ->
            try {
                if (LoginActivity.userID == "") {   //로그인 하지 않았을 경우
                    binding.buttonScrap.isChecked = false
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Please login and Save post.")
                        .setMessage("Do you want to login?")
                        .setPositiveButton("yes",
                            DialogInterface.OnClickListener { dialog, id ->
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            })
                        .setNegativeButton("no",
                            DialogInterface.OnClickListener { dialog, id ->

                            })
                    // 다이얼로그를 띄워주기
                    builder.show()
                } else if (LoginActivity.userType == false) {   //선생님으로 로그인 했을 경우
                    binding.buttonScrap.isChecked = false
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Only students can do the scraps.") //학생만 스크랩 할 수 있다고 안내
                        .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, id ->
                            })
                    // 다이얼로그를 띄워주기
                    builder.show()
                } else if (isChecked) { //스크랩 하기
                    Log.d("checked", isChecked.toString())
                    compoundButton.startAnimation(scaleAnimation)
                    if (postID != null) {
                        requestSaveScrapPost(postID.orEmpty(), LoginActivity.userID)
                    }
                } else if (!isChecked) { //스크랩 취소하기
                    Log.d("checked", isChecked.toString())
                    compoundButton.startAnimation(scaleAnimation)
                    if (postID != null) {
                        requestCancelScrapPost(scrapID.orEmpty())
                    }
                }

            } catch (err: Exception) {
                binding.heartNumTextView.text = heartNum
                binding.scrapNumTextView.text = scrapNum
            }
        }


        //댓글 작성하기 기능
        binding.commentSendButton.setOnClickListener {
            try{
                val commentContent = binding.commentEditText.text.toString()
                if (LoginActivity.userID == "") {   //로그인 하지 않았을 경우

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Please login and enter your comment.")
                        .setMessage("Do you want to login?")
                        .setPositiveButton("yes",
                            DialogInterface.OnClickListener { dialog, id ->
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            })
                        .setNegativeButton("no",
                            DialogInterface.OnClickListener { dialog, id ->

                            })
                    // 다이얼로그를 띄워주기
                    builder.show()
                } else if (commentContent == "") { //댓글의 글자를 입력하지 않았을 경우
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Please enter your comment")
                        .setMessage("Enter at least 10 characters")
                        .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, id ->
                            })
                    // 다이얼로그를 띄워주기
                    builder.show()
                } else {
                    sendComment(postID.orEmpty(), LoginActivity.userID, commentContent)
                    binding.commentEditText.text = null

                }
            }catch (err:Exception){

            }

        }


    }
    //댓글 삭제하기
     fun requestDeleteComment(comment: Comment){
         val builder = AlertDialog.Builder(this)
         builder.setTitle("Are you sure you want to delete it?")
             .setPositiveButton("yes",
                 DialogInterface.OnClickListener { dialog, id ->

                     val url = "http://34.168.110.14:8080/deleteComment/${comment.comtID}"
                     //요청 객체만들기
                     val request = object : StringRequest(
                         //요청
                         Request.Method.DELETE,
                         url,
                         //응답
                         {
                             //성공일때

                             if(commentList.size==0){
                                 binding.commentMsgTextview.visibility= VISIBLE
                             }
                             Log.d("댓글 삭제 성공","응답->${it}")


                         },
                         {
                             //에러일때
                             Log.d("err", "응답->${it.message}")
                         }
                     ) {

                     }

                     //요청하고 응답받으면 동일한 주소로보내면 전에받았던걸 그대로보여줄수도있는데
                     //실시간으로 보고싶을때 false로 해준다
                     request.setShouldCache(false)
                     //큐가알아서 요청을 보내고 응답을 받는다
                     requestQueue?.add(request)
                 })
             .setNegativeButton("no",
                 DialogInterface.OnClickListener { dialog, id ->

                 })
         // 다이얼로그를 띄워주기
         builder.show()

        commentList.remove(comment)
        binding.rvComment.adapter = CommentAdapter(commentList)


    }
    //댓글 수정하기 기능
    fun requestEditComment(comment: Comment,position:Int,updateCommentText:String){
        val url = "http://34.168.110.14:8080/updateComment"
        // 2. Request Obejct인 StringRequest 생성
        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                Log.d("댓글 수정 성공","응답->${response}")
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["comtID"] = comment.comtID
                params["comt_content"] = updateCommentText

                return params
            }

        }
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)

        comment.comt_content=updateCommentText
        commentList.set(position,comment)

        binding.rvComment.adapter = CommentAdapter(commentList)

    }

    private fun viewEduPostUserClicked(postID: String, userID: String) {
        val url = "http://34.168.110.14:8080/returnViewHeart"
        // 2. Request Obejct인 StringRequest 생성
        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val gson = Gson()
                val eduPostUserClickedState =
                    gson.fromJson(response, EduPostUserClicked::class.java)
                heartOnClicked = eduPostUserClickedState.heartOnClicked
                scrapOnClicked = eduPostUserClickedState.scrapOnClicked

                binding.buttonFavorite.isChecked= heartOnClicked
                binding.buttonScrap.isChecked = scrapOnClicked

                Log.d("heartclicked",heartOnClicked.toString())
                Log.d("scrapclicked",scrapOnClicked.toString())

                heartID=eduPostUserClickedState.heartID
                scrapID=eduPostUserClickedState.scrapID

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "1"+error.toString(), Toast.LENGTH_LONG).show()
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

    private fun sendComment(postID: String, userID: String, comt_content: String) {
        val url = "http://34.168.110.14:8080/writeComment"
        // 2. Request Obejct인 StringRequest 생성
        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                Log.d("commentListSize",commentList.size.toString())
                if(commentList.size==0){
                    binding.commentMsgTextview.visibility= GONE
                    binding.rvComment.visibility= VISIBLE
                }

                    val gson = Gson()
                    val commentResponse = gson.fromJson(response, CommentResponse::class.java)
                    Log.d("댓글 작성 성공!!",response)
                    val result: ArrayList<Comment> = commentResponse.commentList;
                    val savedComment: Comment = result.get(0)

                    commentList.add(savedComment)
                    binding.rvComment.adapter = CommentAdapter(commentList)



            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["postID"] = postID
                params["userID"] = userID
                params["comt_content"] = comt_content

                return params
            }

        }
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)



    }

    //하트 수 올리기 기능
    private fun requestUpHeart(postID: String, userID: String) {


        val url = "http://34.168.110.14:8080/upPostHeart/"

        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val gson = Gson()
                val heartPostResponse = gson.fromJson(response, HeartPostResponse::class.java)
                if(!heartPostResponse.success){
                    binding.heartNumTextView.text=heartNum
                }
                else{
                    heartID = heartPostResponse.heartID
                    binding.heartNumTextView.text=heartPostResponse.heart.toString()
                }



            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "2"+error.toString(), Toast.LENGTH_LONG).show()

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

    private fun requestDownHeart(heartID: String) {
        val url = "http://34.168.110.14:8080/deleteHeart/${heartID}"

        val request = object : StringRequest(
            //요청
            Request.Method.DELETE,
            url,
            //응답
            {
                //정상응답일때
                val gson = Gson()
                val heartPostResponse = gson.fromJson(it, HeartPostResponse::class.java)

                this.heartID=heartPostResponse.heartID
                binding.heartNumTextView.text=heartPostResponse.heart.toString()
            },
            {
                //에러일때
                Log.d("err", "응답->${it.message}")
            }
        ) {

        }

        //요청하고 응답받으면 동일한 주소로보내면 전에받았던걸 그대로보여줄수도있는데
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)
    }

    //게시글 스크랩 하기 기능
    private fun requestSaveScrapPost(postID: String, userID: String) {

        val url = "http://34.168.110.14:8080/scrap"
        //요청 객체만들기
        // 2. Request Obejct인 StringRequest 생성
        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val gson = Gson()
                val scrapPostResponse = gson.fromJson(response, ScrapPostResponse::class.java)

                if(!scrapPostResponse.success){
                    binding.scrapNumTextView.text=scrapNum
                }
                else{
                    scrapID = scrapPostResponse.scrapID
                    binding.scrapNumTextView.text=scrapPostResponse.scrap.toString()
                }


            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "3"+error.toString(), Toast.LENGTH_LONG).show()

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
    //스크랩 게시글 삭제하기 기능
    private fun requestCancelScrapPost(scrapID: String) {
        val url = "http://34.168.110.14:8080/deleteScrap/${scrapID}"
        //요청 객체만들기
        val request = object : StringRequest(
            //요청
            Request.Method.DELETE,
            url,
            //응답
            {
                //정상응답일때
                val gson = Gson()
                val scrapPostResponse = gson.fromJson(it, ScrapPostResponse::class.java)

                this.scrapID = scrapPostResponse.scrapID
                binding.scrapNumTextView.text=scrapPostResponse.scrap.toString()


            },
            {
                //에러일때
                Log.d("err", "응답->${it.message}")
            }
        ) {

        }

        //요청하고 응답받으면 동일한 주소로보내면 전에받았던걸 그대로보여줄수도있는데
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)

    }

    //비회원인 경우 댓글리스트 요청하기
    private fun requestCommentList(postID: String) {

        val url = "http://34.168.110.14:8080/commentListPost/${postID}"
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
                Log.d("err", "응답->${it.message}")
            }
        ) {

        }

        //요청하고 응답받으면 동일한 주소로보내면 전에받았던걸 그대로보여줄수도있는데
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)

    }
    //회원인 경우 댓글 리스트 요청하기
    private fun requestCommentListLogin(postID: String,userID:String) {

        val url = "http://34.168.110.14:8080/commentListPost/${postID}/${userID}"
        //요청 객체만들기
        val request = object : StringRequest(
            //요청
            Request.Method.GET,
            url,
            //응답
            {
                //정상응답일때

                Log.d("success:","응답->$it")
                commentListProcessResponse(it)
            },
            {
                //에러일때
                Log.d("err", "응답->${it.message}")
            }
        ) {

        }

        //요청하고 응답받으면 동일한 주소로보내면 전에받았던걸 그대로보여줄수도있는데
        //실시간으로 보고싶을때 false로 해준다
        request.setShouldCache(false)
        //큐가알아서 요청을 보내고 응답을 받는다
        requestQueue?.add(request)

    }


    fun commentListProcessResponse(response: String) {
        val gson = Gson()
        val commentResponse = gson.fromJson(response, CommentResponse::class.java)
        if(!commentResponse.msg.isNullOrEmpty()){
            binding.commentMsgTextview.visibility= VISIBLE
            binding.commentMsgTextview.text=commentResponse.msg
        }
        else{
            binding.commentMsgTextview.visibility = GONE
            commentList = commentResponse.commentList;

            binding.rvComment.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvComment.setHasFixedSize(true)

            binding.rvComment.adapter = CommentAdapter(commentList)
        }

    }

}