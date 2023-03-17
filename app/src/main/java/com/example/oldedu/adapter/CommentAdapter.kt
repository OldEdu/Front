package com.example.oldedu.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import androidx.core.text.set
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.bumptech.glide.Glide.init
import com.example.oldedu.educated3
import com.example.oldedu.databinding.ItemCommentBinding
import com.example.oldedu.model.Comment
import kotlinx.android.synthetic.main.item_contents.view.*

class CommentAdapter(val commentList:ArrayList<Comment>): RecyclerView.Adapter<CommentAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.CustomViewHolder, position: Int) {
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

        var updateCommentText=""
        holder.btnCommentModify.setOnClickListener {
            holder.commentContent.visibility= GONE
            holder.editLayout.visibility= VISIBLE
            educated3Activity?.binding?.commentEditLayout?.visibility= GONE
            holder.editTextComment.setText(commentList[position].comt_content)


        }

        holder.btnEditComment.setOnClickListener {
            updateCommentText= holder.editTextComment.text.toString()
            educated3Activity?.requestEditComment(commentList[position],position,updateCommentText)
            holder.commentContent.visibility= VISIBLE
            holder.editLayout.visibility= GONE
            educated3Activity?.binding?.commentEditLayout?.visibility= VISIBLE
        }



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
        val editLayout=binding.editLayout
        val editTextComment=binding.editTextComment
        val btnEditComment=binding.btnEditComment

    }


}




