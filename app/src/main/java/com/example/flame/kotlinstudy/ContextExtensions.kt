package com.example.flame.kotlinstudy

import android.content.Context
import android.widget.Toast

/**
 * Created by flame on 2018/2/17.
 */
fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()