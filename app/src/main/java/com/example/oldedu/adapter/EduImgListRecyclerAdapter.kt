package com.example.oldedu.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oldedu.*
import com.example.oldedu.EduImgListActivity
import com.example.oldedu.modifyPhoto.ModifyUploadEduImgActivity
import kotlinx.android.synthetic.main.activity_upload_edu_text.*

class EduImgListRecyclerAdapter(val postList : List<ImgItemData>, val context : Context) : RecyclerView.Adapter<EduImgListRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.edu_img_item_recyclerview,parent ,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position],context)
//        holder.itemView.setOnClickListener{
//            val intent = Intent(holder.itemView.context , ModifyUploadEduImgActivity::class.java)
//            intent.putExtra("eduPhotoID" , postList[position].eduPhotoID)
//            holder.itemView.context.startActivity(intent)
//        }
        holder.btn?.setOnClickListener {
            val intent = Intent(holder.itemView.context , ModifyUploadEduImgActivity::class.java)
            intent.putExtra("eduPhotoID" , postList[position].eduPhotoID)
            holder.itemView.context.startActivity(intent)
        }
        holder.textContent?.text = postList[position].textGuide
        holder.img?.let {
            Glide.with(holder.itemView)
                .load(postList[position].imgUrl)
                .fallback(R.drawable.logo)
                .into(it)
        }
    }

    override fun getItemCount(): Int {
        return postList.count()
    }

    class ViewHolder (itemView : View?) : RecyclerView.ViewHolder(itemView!!) {
        //        val storageReference = Firebase.storage.reference
        val img = itemView?.findViewById<ImageView>(R.id.imgView_thumbnail)
        val textContent = itemView?.findViewById<TextView>(R.id.text_content)
        val btn = itemView?.findViewById<Button>(R.id.btn_mod)

        fun bind (item : ImgItemData?, context: Context) {


//            item?.imgUrl?.let { url ->
//                Log.d("))))) " , "url 있다 ? ")
//                Glide.with(itemView)
//                    .load(url)
//                    .fallback(R.drawable.logo)
//                    .into(img!!)
//            }

        }
    }
}