package com.study.thesuperiorstanislav.decisiontheorylabs.lab3

import com.study.thesuperiorstanislav.decisiontheorylabs.BasePresenter
import com.study.thesuperiorstanislav.decisiontheorylabs.BaseView
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD

interface GraphListContact {
    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun setData(pointListRestored: List<PointMD>)

        fun onError(error: UseCase.Error)

    }

    interface Presenter : BasePresenter
}