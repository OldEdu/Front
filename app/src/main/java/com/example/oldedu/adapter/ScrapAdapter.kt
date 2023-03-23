package com.example.oldedu.adapter

import android.content.Intent
import android.util.Log
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
import org.w3c.dom.Text

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
        holder.category.text = scrapList[position].category
        holder.in_date.text = scrapList[position].in_date
        holder.heart.text = scrapList[position].heart.toString()
        holder.comment.text = scrapList[position].comment.toString()
        holder.views.text = scrapList[position].views.toString()
        holder.userName.text = scrapList[position].userName

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, Detail::class.java)
            var edumodel = edu(scrapList[position].userID,
                scrapList[position].title,
                scrapList[position].userName,
                scrapList[position].category,
                scrapList[position].heart,
                scrapList[position].views,
                scrapList[position].postID,
                scrapList[position].comment,
                scrapList[position].scrap,
                scrapList[position].in_date)

            intent.putExtra("edumodel", edumodel)
            holder.itemView.context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
        return scrapList.size

    }
    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val scraptitle = itemView.findViewById<TextView>(R.id.scraptitle)
        val category = itemView.findViewById<TextView>(R.id.categoryText)
        val in_date = itemView.findViewById<TextView>(R.id.in_dateText)
        val heart = itemView.findViewById<TextView>(R.id.heartcount)
        val comment = itemView.findViewById<TextView>(R.id.commentcount)
        val views = itemView.findViewById<TextView>(R.id.viewcount)
        val userName = itemView.findViewById<TextView>(R.id.usernameText)


    }

}