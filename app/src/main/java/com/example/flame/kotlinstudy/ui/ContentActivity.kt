package com.example.flame.kotlinstudy.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.lib.LadyFragmentAdapter
import com.example.flame.kotlinstudy.model.Constants
import com.example.flame.kotlinstudy.model.Site
import com.example.flame.kotlinstudy.utils.getCurrSite
import com.example.flame.kotlinstudy.utils.openActivity
import com.example.flame.kotlinstudy.utils.saveCurrSite
import com.example.flame.kotlinstudy.utils.toast
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
        val siteType = this.getCurrSite()
        val adapter = LadyFragmentAdapter(supportFragmentManager, Site.provideSite(siteType), type
                ?: 0)
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

    private fun showDialog() {
        AlertDialog.Builder(this).setTitle("切换站点").setSingleChoiceItems(Site.titles, getCurrSite())
        { dialog, which ->
            dialog.dismiss()
            when(which){
                3-> toast("see you later")
                4-> toast("see you later")
                5 -> toast("see you later")
                2 -> this.openActivity(LiveDataActivity::class.java)
                else -> {
                    this.saveCurrSite(which)
                    this.openActivity(ContentActivity::class.java, Constants.KEY_TYPE, Constants.HOME)
                }
            }

        }.show()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_home ->
                 this.openActivity(ContentActivity::class.java, Constants.KEY_TYPE, Constants.HOME)
            R.id.nav_type ->
                this.openActivity(ContentActivity::class.java, Constants.KEY_TYPE, Constants.CATEGORY)
            R.id.nav_favorite ->
                this.openActivity(GirlOverViewActivity::class.java, Constants.KEY_FAVORITE, true)
            R.id.nav_select -> showDialog()
            R.id.nav_tag -> this.openActivity(GirlTagActivity::class.java,Constants.KEY_URL,Site.currSite?.tagPath)
        }
        drawer_layout.closeDrawer(Gravity.START)
        return true
    }
}
