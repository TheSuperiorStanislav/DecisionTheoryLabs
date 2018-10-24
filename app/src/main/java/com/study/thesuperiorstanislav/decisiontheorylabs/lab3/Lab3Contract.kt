package com.study.thesuperiorstanislav.decisiontheorylabs.lab3

import com.study.thesuperiorstanislav.decisiontheorylabs.BasePresenter
import com.study.thesuperiorstanislav.decisiontheorylabs.BaseView
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD

interface Lab3Contract {
    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun showData(pointListOriginal: List<Point>,pointListRestored: List<Point>,
                       pointListCs: Array<Double>)

        fun onError(error: UseCase.Error)

        fun onLoadingError(error: UseCase.Error)

    }

    interface Presenter : BasePresenter {

        fun doTheThing(pointList:List<PointMD>)

        fun getPoints()

        fun savePoint(pointList: List<PointMD>)

    }
}