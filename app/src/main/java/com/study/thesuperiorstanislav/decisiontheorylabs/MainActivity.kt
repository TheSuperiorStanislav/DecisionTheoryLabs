package com.study.thesuperiorstanislav.decisiontheorylabs

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.Repository
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.Lab1Fragment
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.Lab1Presenter
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.DoTheThing
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.GetData
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.CacheDataFromFile
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.Lab2Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        if (supportFragmentManager.fragments.size > 0)
            this.welcome_text.visibility = View.GONE

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        if (item.isChecked)
            return true

        when (item.itemId) {
            R.id.nav_lab_1 -> {
                val ft = supportFragmentManager.beginTransaction()
                val fragment = Lab1Fragment()
                fragment.setPresenter(Lab1Presenter(fragment,
                        DoTheThing(),
                        GetData(Repository),
                        CacheDataFromFile(Repository)))
                ft.replace(R.id.content_frame, fragment)
                if (!isFinishing)
                    ft.commitAllowingStateLoss()
                this.welcome_text.visibility = View.GONE
            }
            R.id.nav_lab_2 -> {
                val ft = supportFragmentManager.beginTransaction()
                val fragment = Lab2Fragment()
//                fragment.setPresenter(Lab1Presenter(fragment,
//                        DoTheThing(),
//                        GetData(Repository),
//                        CacheDataFromFile(Repository)))
                ft.replace(R.id.content_frame, fragment)
                if (!isFinishing)
                    ft.commitAllowingStateLoss()
                this.welcome_text.visibility = View.GONE
            }
            R.id.nav_lab_3 -> {

            }
            R.id.nav_lab_4 -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
