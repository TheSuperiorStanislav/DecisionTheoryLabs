package com.study.thesuperiorstanislav.decisiontheorylabs.lab5

import com.study.thesuperiorstanislav.decisiontheorylabs.BasePresenter
import com.study.thesuperiorstanislav.decisiontheorylabs.BaseView
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

interface Lab5Contract {
    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun drawGraph(pointListOriginal: List<Point>,
                      pointListRestored: List<Point>)

        fun onError(error: UseCase.Error)

        fun onLoadingError(error: UseCase.Error)

    }

    interface Presenter : BasePresenter {

        fun getData()

        fun doTheThing(n: Int, delta: Double, function: String, alpha: Double)

    }
}