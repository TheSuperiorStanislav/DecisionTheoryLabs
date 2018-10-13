package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1

import com.study.thesuperiorstanislav.decisiontheorylabs.BasePresenter
import com.study.thesuperiorstanislav.decisiontheorylabs.BaseView
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

interface Lecture1Contract {
    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun showData(function: String,
                     pointList: List<Point>,
                     value: Double,
                     alpha: Double)

        fun onError(error: UseCase.Error)

        fun onLoadingError(error: UseCase.Error)

    }

    interface Presenter : BasePresenter {

        fun getData()

        fun setRunStats(isRunning: Boolean)

        fun startTheThing(function: String, alpha: Double, value: Double)

        fun stopTheThing()

        fun changeAlpha(flag: Boolean)

        fun doTheThing(function: String, pointList: MutableList<Point>, alpha: Double, value: Double)
    }
}