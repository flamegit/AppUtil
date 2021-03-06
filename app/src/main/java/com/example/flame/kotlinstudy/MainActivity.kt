package com.example.flame.kotlinstudy

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.flame.kotlinstudy.lib.CommonAdapter
import com.example.flame.kotlinstudy.ui.*
import com.example.flame.kotlinstudy.utils.openActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val items = arrayOf("DataBinding Example", "LiveData Example", " Simple DataBinding Demo", "operator", "test")
    private val targets = arrayOf(DataBindingActivity::class.java,
            LiveDataActivity::class.java, UserActivity::class.java,
            OperatorsStudyActivity::class.java, TestWebSocketActivity::class.java)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                openActivity(NavigationDrawerActivity::class.java)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                openActivity(FileExplorerActivity::class.java)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = CommonAdapter<String>(android.R.layout.simple_list_item_1) { holder, position, data ->
            holder.get<TextView>(android.R.id.text1).text = data
            holder.itemView.setOnClickListener {
                openActivity(targets[position])
            }
        }
        adapter.addItems(items.asList(), false)
        recycler_view.adapter = adapter
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
