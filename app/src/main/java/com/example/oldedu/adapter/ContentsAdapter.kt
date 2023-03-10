package com.example.oldedu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oldedu.R
import com.example.oldedu.databinding.ItemContentsBinding
import com.example.oldedu.model.Mycontents

class ContentsAdapter (val contentsList : ArrayList<Mycontents>):RecyclerView.Adapter<ContentsAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contents,parent,false)
        return CustomViewHolder(view)


    }

    override fun onBindViewHolder(holder: ContentsAdapter.CustomViewHolder, position: Int) {
        holder.contentstitle.text = contentsList[position].title


    }

    override fun getItemCount(): Int {
        return contentsList.size

    }


    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

      val contentstitle = itemView.findViewById<TextView>(R.id.contentitle)//컨텐츠 이름 가져오기

    }


}