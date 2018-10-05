package com.study.thesuperiorstanislav.decisiontheorylabs.data.source

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point

interface DataSource {
    interface LoadPointCallback {

        fun onPointLoaded(pointList: List<Point>)

        fun onDataNotAvailable(error: UseCase.Error)
    }

    interface SavePointCallback {

        fun onSaved()

        fun onError(error:UseCase.Error)
    }

    fun getPointsLab1(callback: LoadPointCallback)

    fun cachePointsLab1(pointList: List<Point>, callback: SavePointCallback)

}