package com.example.flame.kotlinstudy.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.flame.kotlinstudy.model.Constants

class GirlListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState==null){
            val url =intent.getStringExtra(Constants.KEY_URL)
             supportFragmentManager.beginTransaction()
                     .add(android.R.id.content,GirlListFragment.newInstance(url))
                     .commit()
        }
        //setContentView(R.layout.activity_gilr_list)
    }
}
