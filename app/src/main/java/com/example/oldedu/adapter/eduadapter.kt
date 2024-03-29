package com.example.oldedu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.oldedu.databinding.ItemLifeBinding
import com.example.oldedu.model.edu

class eduadapter(private var itemClickedListener: (edu)->Unit): ListAdapter<edu, eduadapter.eduItemViewHolder>(diffUtil) {
    inner class eduItemViewHolder(private val binding: ItemLifeBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(edumodel:edu){
            binding.categoryText.text = edumodel.category
            binding.titletext.text = edumodel.title
            binding.inDateText.text=edumodel.in_date
            binding.usernameText.text = "Writer : ${edumodel.userName}"
            binding.heartcount.text = "heart : ${edumodel.heart}"
            binding.viewcount.text = "view : ${edumodel.views}"
            binding.commentcount.text = "comment : ${edumodel.comment}"

            binding.root.setOnClickListener{
                itemClickedListener(edumodel)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): eduItemViewHolder {
        return eduItemViewHolder(ItemLifeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: eduItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object :DiffUtil.ItemCallback<edu>(){
            override fun areItemsTheSame(oldItem: edu, newItem: edu): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: edu, newItem: edu): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }
}