package com.study.thesuperiorstanislav.decisiontheorylabs.data.source

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.math.Regulator

interface DataSource {
    interface LoadPointCallback {

        fun onPointLoaded(pointList: List<Point>)

        fun onDataNotAvailable(error: UseCase.Error)
    }

    interface SavePointCallback {

        fun onSaved()

    }

    interface LoadPointMDCallback {

        fun onPointMDLoaded(pointList: List<PointMD>)

        fun onDataNotAvailable(error: UseCase.Error)
    }

    interface SavePointMDCallback {

        fun onSaved()

    }

    interface LoadRegulatorReturnDataCallback {

        fun onRegulatorReturnDataLoaded(regulatorReturnData: Regulator.RegulatorReturnData)

        fun onDataNotAvailable(error: UseCase.Error)
    }

    interface SaveRegulatorReturnDataCallback {

        fun onSaved()

    }

    fun getPointsLab1(callback: LoadPointCallback)

    fun cachePointsLab1(pointList: List<Point>, callback: SavePointCallback)

    fun getPointsLab2(callback: LoadPointCallback)

    fun cachePointsLab2(pointList: List<Point>, callback: SavePointCallback)

    fun getPointsLab3(callback: LoadPointMDCallback)

    fun cachePointsLab3(pointList: List<PointMD>, callback: SavePointMDCallback)

    fun getDataLab5(callback: LoadRegulatorReturnDataCallback)

    fun cacheDataLab5(regulatorReturnData: Regulator.RegulatorReturnData, callback: SaveRegulatorReturnDataCallback)

}