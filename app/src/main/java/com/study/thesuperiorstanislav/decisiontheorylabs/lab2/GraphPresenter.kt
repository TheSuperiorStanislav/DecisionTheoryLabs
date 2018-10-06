package com.study.thesuperiorstanislav.decisiontheorylabs.lab2

import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

class GraphPresenter(private val graphView: GraphContract.View,
                     val pointListOriginal: List<Point>,
                     val pointListRestored: List<Point>) :GraphContract.Presenter {


    override fun start() {
        graphView.isActive = true
        graphView.drawGraph(pointListOriginal, pointListRestored)
    }
}