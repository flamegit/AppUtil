package com.example.flame.kotlinstudy

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.flame.kotlinstudy.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            var binding: ActivityUserBinding =  DataBindingUtil.setContentView(this,R.layout.activity_user);
            var user = User("dd", "万",12 )
            binding.users=user
        }


}
