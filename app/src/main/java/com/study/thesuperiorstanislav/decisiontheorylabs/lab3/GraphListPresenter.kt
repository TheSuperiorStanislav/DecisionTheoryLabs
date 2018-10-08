package com.study.thesuperiorstanislav.decisiontheorylabs.lab3

import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD

class GraphListPresenter(private val graphListView:GraphListContact.View,
                         private val pointListRestored:List<PointMD>):GraphListContact.Presenter {

    override fun start() {
        graphListView.isActive = true
        graphListView.setData(pointListRestored)
    }
}