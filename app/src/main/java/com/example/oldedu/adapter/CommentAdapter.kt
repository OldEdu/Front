package com.example.oldedu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oldedu.R
import com.example.oldedu.databinding.ActivityDetailBinding
import com.example.oldedu.databinding.ItemCommentBinding
import com.example.oldedu.model.Comment

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

    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    class CustomViewHolder(binding:ItemCommentBinding):RecyclerView.ViewHolder(binding.root){
        val commentContent= binding.comtContentTextView
        val nickName=binding.nickNameTextView
        val date=binding.dateTextView

    }

}


