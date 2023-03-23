package com.example.oldedu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_eduphoto_page.*

class PageFragment : Fragment() {

    var imgNum=0
    var title:String?=null
    var imgUrl:String?=null
    var textGuide:String?=null
    var voiceGuide:String?=null
    var eduPhotoID:String?=null

    companion object{

        fun newInstance(title:String,eduPhotoID:String, imgUrl:String, textGuide:String,voiceGuide:String,imgNum:Int):PageFragment{
            val fragment = PageFragment()

            //액티비티 위에올라가야 초기화가되는거임
            //그때 사용할 임시데이터로 argument를 넣어준것이다.

            val bundle = Bundle()
            bundle.putInt("imgNum",imgNum)
            bundle.putString("title",title)
            bundle.putString("imgUrl",imgUrl)
            bundle.putString("eduPhotoID",eduPhotoID)
            bundle.putString("textGuide",textGuide)
            bundle.putString("voiceGuide",voiceGuide)
            fragment.arguments=bundle

            return fragment
        }

    }
    //실제 프레그먼트가 액티비티위에올라가 초기화 될 시점에 onCreate가 호출될것이다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //같은뜻 if(arguments != null){ }
        //this가 이제 arguments다.
        //임시로 넣어놨던것을 할당해주었다.
        arguments?.apply{
            imgNum=getInt("imgNum")
            imgUrl=getString("imgUrl")
            title=getString("title")
            voiceGuide=getString("voiceGuide")
            textGuide=getString("textGuide")
            eduPhotoID=getString("eduPhotoID")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eduphoto_page, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(imgUrl)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .dontAnimate()
            .into(eduPhotoImageView);
    }



}