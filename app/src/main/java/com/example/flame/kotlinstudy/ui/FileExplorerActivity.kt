package com.example.flame.kotlinstudy.ui

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import android.widget.TextView
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.utils.openActivity
import kotlinx.android.synthetic.main.activity_behavior2.*
import java.io.File
import android.os.Build
import android.view.View


class FileExplorerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

        val path = intent.getStringExtra("PATH")

        var file = Environment.getExternalStorageDirectory()

        if (path != null) {
            file = File(path)
        }
        //path_view.text=file.path

        val adapter = CommonAdapter<File>(android.R.layout.simple_list_item_1) { holder, _, data ->
            holder.get<TextView>(android.R.id.text1).text = data.name
            holder.itemView.setOnClickListener {
                if (data.isDirectory) {
                    //data.path
                    openActivity(FileExplorerActivity::class.java, "PATH", data.path)
                } else {
                    var intent = Intent(Intent.ACTION_VIEW)
                    //intent.setType("/")
                    startActivity(intent)
                }
            }
        }

        adapter.addItems(file.listFiles().asList(), false)
        file_recycler_view.adapter = adapter
    }

    fun getType(file: File): String {

        return "text/*"
    }



}
