package com.study.thesuperiorstanislav.decisiontheorylabs.lab1

import com.study.thesuperiorstanislav.decisiontheorylabs.BasePresenter
import com.study.thesuperiorstanislav.decisiontheorylabs.BaseView
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

interface Lab1Contract {
    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun drawGraph(pointListOriginal: List<Point>,
                      pointListRestored: List<Point>,
                      abc:DoubleArray)

        fun onError(error: UseCase.Error)

        fun onLoadingError(error: UseCase.Error)

    }

    interface Presenter : BasePresenter {

        fun doTheThing(pointList:List<Point>)

        fun getPoints()

        fun savePoint(pointList: List<Point>)

    }
}
