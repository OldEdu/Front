package com.example.oldedu.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun getItemCount(): Int {
        return postList.count()
    }

    class ViewHolder (itemView : View?) : RecyclerView.ViewHolder(itemView!!) {
        //        val storageReference = Firebase.storage.reference
        val img = itemView?.findViewById<ImageView>(R.id.imgView_thumbnail)
        val textContent = itemView?.findViewById<TextView>(R.id.text_content)
        val btnUrl = itemView?.findViewById<TextView>(R.id.btn_mod)

        fun bind (item : ImgItemData?, context: Context) {
            textContent?.text = item?.textGuide
            if (item != null) {
                Log.d("photo---" , item.imgUrl)
            }

            btnUrl?.setOnClickListener{
                val intent = Intent(context, ModifyUploadEduImgActivity::class.java)
                intent.apply {
                    this.putExtra("postID",item?.postID)
                    this.putExtra("eduPhotoID" , item?.eduPhotoID)
                }
                startActivity(context, intent, Bundle())
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