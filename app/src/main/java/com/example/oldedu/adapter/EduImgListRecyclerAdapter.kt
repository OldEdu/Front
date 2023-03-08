package com.example.oldedu.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oldedu.ImgItemData
import com.example.oldedu.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EduImgListRecyclerAdapter(val postList : List<ImgItemData>, val context : Context) : RecyclerView.Adapter<EduImgListRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.edu_img_item_recyclerview,parent ,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position],context)
    }

    override fun getItemCount(): Int {
        return postList.count()
    }

    class ViewHolder (itemView : View?) : RecyclerView.ViewHolder(itemView!!) {
        //        val storageReference = Firebase.storage.reference
        val img = itemView?.findViewById<ImageView>(R.id.imgView_thumbnail)
        val num = itemView?.findViewById<TextView>(R.id.text_num)
        val textContent = itemView?.findViewById<TextView>(R.id.text_content)
//        val btnUrl = itemView?.findViewById<TextView>(R.id.btn_mod)

        fun bind (item : ImgItemData?, context: Context) {
            num?.text = item?.imgNum.toString()
            textContent?.text = item?.textGuide
            if (item != null) {
                Log.d("photo---" , item.imgUrl)
            }

            item?.imgUrl?.let { url ->
                Log.d("))))) " , "url 있다 ? ")
                Glide.with(itemView)
                    .load(url)
                    .fallback(R.drawable.logo)
                    .into(img!!)
            }

        }
    }
}