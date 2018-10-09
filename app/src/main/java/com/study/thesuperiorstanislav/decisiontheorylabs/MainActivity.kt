package com.study.thesuperiorstanislav.decisiontheorylabs

import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.Repository
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.Lab1Fragment
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.Lab1Presenter
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.DoTheThingLab1
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.GetDataLab1
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.usecase.CacheDataFromFileLab1
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.Lab2Fragment
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.Lab2Presenter
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase.CacheDataFromFileLab2
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase.DoTheThingLab2
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase.GetDataLab2
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.Lab3Fragment
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.Lab3Presenter
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase.CacheDataFromFileLab3
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase.DoTheThingLab3
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.usecase.GetDataLab3
import com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.Lecture1Fragment
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

    override fun onResume() {
        super.onResume()
        showFragment(nav_view.checkedItem?.itemId)
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

        showFragment(item.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showFragment(id: Int?) {
        if (id == null)
            return

        when (id) {
            R.id.nav_lab_1 -> {
                val ft = supportFragmentManager.beginTransaction()
                val fragment = Lab1Fragment()
                fragment.setPresenter(Lab1Presenter(fragment,
                        DoTheThingLab1(),
                        GetDataLab1(Repository),
                        CacheDataFromFileLab1(Repository)))
                ft.replace(R.id.content_frame, fragment)
                if (!isFinishing)
                    ft.commitAllowingStateLoss()
                this.welcome_text.visibility = View.GONE
            }
            R.id.nav_lab_2 -> {
                val ft = supportFragmentManager.beginTransaction()
                val fragment = Lab2Fragment()
                fragment.setPresenter(Lab2Presenter(fragment,
                        DoTheThingLab2(),
                        GetDataLab2(Repository),
                        CacheDataFromFileLab2(Repository)))
                ft.replace(R.id.content_frame, fragment)
                if (!isFinishing)
                    ft.commitAllowingStateLoss()
                this.welcome_text.visibility = View.GONE
            }
            R.id.nav_lab_3 -> {
                val ft = supportFragmentManager.beginTransaction()
                val fragment = Lab3Fragment()
                fragment.setPresenter(Lab3Presenter(fragment,
                        DoTheThingLab3(),
                        GetDataLab3(Repository),
                        CacheDataFromFileLab3(Repository)))
                ft.replace(R.id.content_frame, fragment)
                if (!isFinishing)
                    ft.commitAllowingStateLoss()
                this.welcome_text.visibility = View.GONE
            }
            R.id.nav_lab_4 -> {

            }
            R.id.nav_lecture_1 -> {
                val ft = supportFragmentManager.beginTransaction()
                val fragment = Lecture1Fragment()
//                fragment.setPresenter(Lab3Presenter(fragment,
//                        DoTheThingLab3(),
//                        GetDataLab3(Repository),
//                        CacheDataFromFileLab3(Repository)))
                ft.replace(R.id.content_frame, fragment)
                if (!isFinishing)
                    ft.commitAllowingStateLoss()
                this.welcome_text.visibility = View.GONE
            }
        }
    }
}
