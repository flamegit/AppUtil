package com.example.flame.kotlinstudy.ui

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.model.User
import com.example.flame.kotlinstudy.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val binding: ActivityUserBinding =  DataBindingUtil.setContentView(this, R.layout.activity_user)
            val user = User("dd", "万", 12)
            binding.users=user
        }


}
