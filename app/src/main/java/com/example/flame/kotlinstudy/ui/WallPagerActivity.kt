package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.flame.kotlinstudy.R
import android.app.WallpaperManager
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.flame.kotlinstudy.utils.toast


class WallPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wall_pager)

        val action = intent.action
        val type = intent.type
        Log.d("wallpager",action + type)
        if (Intent.ACTION_SEND.equals(action)&&type!=null){
            if ("text/plain".equals(type)){

            }else if(type.startsWith("image/")){
                val uri=intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
                toast(uri.toString())

            }
        }else if (Intent.ACTION_SEND_MULTIPLE.equals(action)&&type!=null){
            if (type.startsWith("image/")){
            }
        }

        val wallpaperManager = WallpaperManager.getInstance(this)
        //wallpaperManager.setBitmap(bitmap)
    }
}
