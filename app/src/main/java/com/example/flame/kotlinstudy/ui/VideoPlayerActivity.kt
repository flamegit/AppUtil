package com.example.flame.kotlinstudy.ui

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.jzvd.JzvdStd
import com.example.flame.kotlinstudy.R
import kotlinx.android.synthetic.main.activity_vedio_player.*
import java.net.URLEncoder

class VideoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vedio_player)

        val url=URLEncoder.encode("https://s3.cn-north-1.amazonaws.com.cn/web-s3.saybot.net/azj/teachers/ALO7 NCE Junior_SD.mp4")
        video_player.setUp(url,"",JzvdStd.SCREEN_WINDOW_NORMAL)

        video_view.setVideoURI(Uri.parse("http://192.168.120.76/ALO7%20NCE%20Junior_SD.mp4"))

        //val uri = "android.resource://" + packageName + "/" + R.raw.test
        //video_view.setVideoPath(uri)
        video_view.start()
        //video_view.setv


    }
}

