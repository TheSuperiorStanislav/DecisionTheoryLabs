package com.study.thesuperiorstanislav.decisiontheorylabs.lab2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class GraphAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {
    private val mFragmentList = mutableListOf<Fragment>()
    private val mFragmentTitleList = mutableListOf<String>()

    override fun getItem(position:Int): androidx.fragment.app.Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position:Int):CharSequence {
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: androidx.fragment.app.Fragment, title:String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}
