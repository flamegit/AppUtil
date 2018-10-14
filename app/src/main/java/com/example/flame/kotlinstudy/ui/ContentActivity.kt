package com.example.flame.kotlinstudy.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.di.component.ActivityComponent
import com.example.flame.kotlinstudy.lib.LadyFragmentAdapter
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.model.Site
import com.example.flame.kotlinstudy.utils.openActivity
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.fragment_content.*

class ContentActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        setSupportActionBar(toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        initView(intent)
    }

    private fun initView(intent: Intent?) {

        val type = intent?.getIntExtra(Constants.KEY_TYPE, Constants.HOME)
        val siteType = intent?.getIntExtra(Constants.KEY_SITE_TYPE, 1)
        val adapter = LadyFragmentAdapter(supportFragmentManager, Site.provideSite(siteType?:0), type ?: 0)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initView(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_home ->
                ContentActivity@ this.openActivity(ContentActivity::class.java, Constants.KEY_TYPE, Constants.HOME)
            R.id.nav_type ->
                ContentActivity@ this.openActivity(ContentActivity::class.java, Constants.KEY_TYPE, Constants.CATEGORY)
            R.id.nav_favorite ->
                ContentActivity@ this.openActivity(ContentActivity::class.java, Constants.KEY_SITE_TYPE, 0)
        }
        return true
    }
}
