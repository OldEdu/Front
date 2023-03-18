package com.example.oldedu.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oldedu.EduImgListActivity
import com.example.oldedu.MyPageTeacher
import com.example.oldedu.R
import com.example.oldedu.databinding.ItemContentsBinding
import com.example.oldedu.model.Mycontents
import com.example.oldedu.mycontents
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


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

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, EduImgListActivity::class.java)
            intent.putExtra("postID", contentsList[position].postID)
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return contentsList.size

    }


    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

      val contentstitle = itemView.findViewById<TextView>(R.id.contentitle)


    }



}