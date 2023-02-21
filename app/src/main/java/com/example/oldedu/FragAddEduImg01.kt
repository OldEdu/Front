package com.example.oldedu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_add_img_text.*
import kotlinx.android.synthetic.main.frag_add_img_mark.*
class FragAddEduImgText:Fragment( ) {
    interface OnDataPassListener {
        fun onDataPass(voice:String, text:String)
    }
    lateinit var dataPassListener: OnDataPassListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPassListener = context as OnDataPassListener
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_add_img_text,container,false)
        var voiceGuide = editText_voice.text.toString()
        var textGuide = editText_text.text.toString()
        btn_submitMsg.setOnClickListener {
            dataPassListener.onDataPass(voiceGuide,textGuide)
        }
        return view
    }

}

class FragAddEduImgMark:Fragment( ) {
    interface OnDataPassListener {
        fun onDataPass(markX:Int, markY:Int)
    }
    lateinit var dataPassListener: OnDataPassListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPassListener = context as OnDataPassListener
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_add_img_mark,container,false)

        var markX = mark_X.text.toString().toInt()
        var markY = mark_Y.text.toString().toInt()
        btn_submitMark.setOnClickListener {
            dataPassListener.onDataPass(markX,markY)
        }
        return view
    }
}



public data class PostInfo(
    val category : String ,
    val title : String ,
    val userID : String
)