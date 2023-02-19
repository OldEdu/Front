package com.example.oldedu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_add_img_text.*
import kotlinx.android.synthetic.main.frag_add_img_mark.*
class FragAddEduImgText:Fragment( ) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_add_img_text,container,false)
        var content = editText.text.toString()
        return view
    }
}

class FragAddEduImgMark:Fragment( ) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_add_img_mark,container,false)

        return view
    }
}