package com.study.thesuperiorstanislav.decisiontheorylabs.lab2

import com.study.thesuperiorstanislav.decisiontheorylabs.BasePresenter
import com.study.thesuperiorstanislav.decisiontheorylabs.BaseView
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

interface GraphContract {
    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun drawGraph(pointListOriginal: List<Point>,
                      pointListRestored: List<Point>)

        fun onError(error: UseCase.Error)

    }

    interface Presenter : BasePresenter 

}