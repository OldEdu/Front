package com.example.oldedu.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oldedu.EduImgListActivity
import com.example.oldedu.R
import com.example.oldedu.model.Scrap

class ScrapAdapter(val scrapList : ArrayList<Scrap>):RecyclerView.Adapter<ScrapAdapter.CustomViewHolder>()
{

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myscrap,parent,false)
        return CustomViewHolder(view)


    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.scraptitle.text = scrapList[position].title


    }

    override fun getItemCount(): Int {
        return scrapList.size

    }

    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val scraptitle = itemView.findViewById<TextView>(R.id.scraptitle)


    }

}