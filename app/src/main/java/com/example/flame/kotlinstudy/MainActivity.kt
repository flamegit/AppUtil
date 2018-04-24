package com.example.flame.kotlinstudy

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.ui.BehaviorActivity
import com.example.flame.kotlinstudy.ui.DataBindingActivity
import com.example.flame.kotlinstudy.ui.LiveDataActivity
import com.example.flame.kotlinstudy.ui.UserActivity
import com.example.flame.kotlinstudy.utils.openActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val items= arrayOf("DataBinding Example","LiveData Example"," Simple DataBinding Demo")
    private val targets= arrayOf(DataBindingActivity::class.java,
            LiveDataActivity::class.java,UserActivity::class.java)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                openActivity(LoginActivity::class.java)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                openActivity(BehaviorActivity::class.java)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter=CommonAdapter<String>(android.R.layout.simple_list_item_1,{holder ,position , data ->
            holder.get<TextView>(android.R.id.text1).text=data
            holder.itemView.setOnClickListener {
                openActivity(targets[position])
            }
        })
        adapter.addItems(items.asList(),false)
        recycler_view.adapter=adapter
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
