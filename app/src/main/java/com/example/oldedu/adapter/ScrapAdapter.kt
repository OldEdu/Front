package com.example.oldedu.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oldedu.Detail
import com.example.oldedu.EduImgListActivity
import com.example.oldedu.R
import com.example.oldedu.databinding.ActivityMyscrapBinding
import com.example.oldedu.model.Scrap
import com.example.oldedu.model.edu

class ScrapAdapter(val scrapList: ArrayList<Scrap>):RecyclerView.Adapter<ScrapAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myscrap,parent,false)
        return CustomViewHolder(view)


    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.scraptitle.text = scrapList[position].title

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, Detail::class.java)
            var edumodel = edu(scrapList[position].title,scrapList[position].postID,scrapList[position].userID,scrapList[position].category,scrapList[position].scrap,scrapList[position].views,scrapList[position].userName,scrapList[position].comment,scrapList[position].scrap,scrapList[position].in_date)

            intent.putExtra("edumodel", edumodel)
            holder.itemView.context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
        return scrapList.size

    }
    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val scraptitle = itemView.findViewById<TextView>(R.id.scraptitle)


    }

    inner class ViewHolder(private val binding: ActivityMyscrapBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: Scrap){

        }
    }

}