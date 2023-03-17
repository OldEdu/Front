package com.example.oldedu.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.bumptech.glide.Glide.init
import com.example.oldedu.educated3
import com.example.oldedu.databinding.ItemCommentBinding
import com.example.oldedu.model.Comment

class CommentAdapter(val commentList:ArrayList<Comment>): RecyclerView.Adapter<CommentAdapter.CustomViewHolder>(){
    var requestQueue: RequestQueue? = null
    var item_position:Int=0
    var comment:Comment?=null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.CustomViewHolder, position: Int) {
        item_position=position
        comment=commentList[position]
        holder.nickName.text=commentList[position].userName
        holder.date.text=commentList[position].comt_date
        holder.commentContent.text=commentList[position].comt_content

        if(!commentList[position].myComment){ //내 코멘트가 아닌 경우 수정,삭제 버튼 비활성화
            holder.btnCommentDelete.visibility = INVISIBLE
            holder.btnCommentModify.visibility= INVISIBLE
        }

        val educated3Activity = educated3.getInstance()

        holder.btnCommentDelete.setOnClickListener {
            educated3Activity?.requestDeleteComment(commentList[position])
        }
//            binding.btnCommentModify.setOnClickListener {
//                educated3?.editMember(mPosition!!, mMember!!)
//            }




//        //댓글 삭제 리스너
//        holder.btnCommentDelete.setOnClickListener {
//
//            Log.d("댓글삭제 리스너",commentList[position].comtID)
//            //requestDeleteComment(commentList[position].comtID)
//            //commentList.remove(commentList[position])
//
//        }
//        //댓글 수정 리스너
//        holder.btnCommentModify.setOnClickListener {
//
//        }


    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    class CustomViewHolder(binding:ItemCommentBinding):RecyclerView.ViewHolder(binding.root) {
        val commentContent = binding.comtContentTextView
        val nickName = binding.nickNameTextView
        val date = binding.dateTextView
        val btnCommentDelete = binding.btnCommentDelete
        val btnCommentModify = binding.btnCommentModify


    }


}




