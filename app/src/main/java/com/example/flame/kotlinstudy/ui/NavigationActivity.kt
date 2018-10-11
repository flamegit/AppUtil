package com.example.flame.kotlinstudy.ui

import android.app.Activity
import android.app.ActionBar
import android.os.Bundle
import android.view.View
import android.support.v4.widget.DrawerLayout

import com.example.flame.kotlinstudy.R

class NavigationActivity : Activity(), NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private var mNavigationDrawerFragment: NavigationDrawerFragment? = null

    /**
     * Used to store the last screen title. For use in [.restoreActionBar].
     */
    private var mTitle: CharSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        mNavigationDrawerFragment = fragmentManager.findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment
        mTitle = title

        // Set up the drawer.
        mNavigationDrawerFragment!!.setUp(
                R.id.navigation_drawer,
                findViewById<View>(R.id.drawer_layout) as DrawerLayout)
    }

    override fun onNavigationDrawerItemSelected(position: Int) {
        // update the main content by replacing fragments
        val fragmentManager = fragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit()
    }

    fun onSectionAttached(number: Int) {
        when (number) {
            1 -> mTitle = getString(R.string.title_section1)
            2 -> mTitle = getString(R.string.title_section2)
            3 -> mTitle = getString(R.string.title_section3)
        }
    }

    fun restoreActionBar() {
        val actionBar = actionBar
        actionBar!!.navigationMode = ActionBar.NAVIGATION_MODE_STANDARD
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.title = mTitle
    }
}
