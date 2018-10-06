package com.study.thesuperiorstanislav.decisiontheorylabs.lab2

import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

class CsGraphPresenter(private val csGraphView: CsGraphContract.View,
                       private val pointListCsOriginal: List<Point>) :CsGraphContract.Presenter {


    override fun start() {
        csGraphView.isActive = true
        csGraphView.drawGraph(pointListCsOriginal)
    }
}