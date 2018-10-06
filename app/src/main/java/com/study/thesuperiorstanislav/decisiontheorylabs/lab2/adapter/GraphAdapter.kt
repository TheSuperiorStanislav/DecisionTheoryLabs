package com.study.thesuperiorstanislav.decisiontheorylabs.lab2.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class GraphAdapter(manager:FragmentManager):FragmentPagerAdapter(manager) {
    private val mFragmentList = mutableListOf<Fragment>()
    private val mFragmentTitleList = mutableListOf<String>()

    override fun getItem(position:Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position:Int):CharSequence {
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title:String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}
