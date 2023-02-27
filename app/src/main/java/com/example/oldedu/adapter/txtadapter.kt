package com.example.oldedu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.oldedu.databinding.ItemTxtBinding
import com.example.oldedu.model.edu
import com.example.oldedu.model.txt

class txtadapter : ListAdapter<txt, txtadapter.eduItemViewHolder>(diffUtil) {
    inner class eduItemViewHolder(private val binding: ItemTxtBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(txtmodel: txt){
            binding.txttext.text = txtmodel.textGuide

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): eduItemViewHolder {
        return eduItemViewHolder(ItemTxtBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: eduItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<txt>(){
            override fun areItemsTheSame(oldItem: txt, newItem: txt): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: txt, newItem: txt): Boolean {
                return oldItem.textGuide == newItem.textGuide
            }

        }
    }
}