package com.study.thesuperiorstanislav.decisiontheorylabs

interface BaseView<T : BasePresenter> {

    fun setPresenter(presenter: T)

}
